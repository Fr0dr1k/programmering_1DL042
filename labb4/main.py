import random,urllib.request,ssl,string

ssl._create_default_https_context = ssl._create_unverified_context
#url_without_emails = "https://user.it.uu.se/~joachim/"
url_with_emails = "https://www.math.uu.se/institutionen/personal/"


def get_source_code(url):
    file_object = urllib.request.urlopen(url)
    content = file_object.read()
    text = content.decode('utf-8')

    print("Data collected")
    return text


def is_valid_email(email_address, at_symbol):
    #En epost är en text sträng som innehåller ett namn följt av ett @ följt av en webbadress
    #Nu så säger vi att ett namn kan vara alla typer av texter som innehåller bara bokstäver, siffror och punkter
    #En webbadress säger vi nu är alla godkänd webbadresser så ett domännamn.toppdomän tex gmail.com
    try:
        email_address = email_address.split(at_symbol)
        if not len(email_address) == 2:
            return False
        for i in email_address[0]:
            if i not in EMAIL_CHARS:
                return False

        for i in email_address[1]:
            if i not in EMAIL_CHARS:
                return False

        return True

    except Exception as e:
        print(e)
        return False



EMAIL_CHARS = string.ascii_letters + string.digits + '.'


def find_name_start(text, at_index):
    first_index = 0
    for index in range(at_index-1, -1, -1): #baklänges!
        if text[index] not in EMAIL_CHARS:
            first_index = index + 1
            break
    return first_index


def find_email_end(text, at_index):
    last_index = len(text)-1
    print(text[at_index])
    for i in range(at_index, len(text)-1):
        if i not in EMAIL_CHARS:
            last_index = i
            break
    return last_index


def find_first_email(text):
    at_index = text.find('@')
    if at_index < 0:
        return None
    name_start = find_name_start(text, at_index)
    if name_start == at_index:
        return None
    return text[name_start: at_index + 1]


def get_all_emails(source_code_string, at_symbol):
    all_potential_email = remove_html_code(source_code_string, at_symbol)
    all_emails = set()

    for emails in all_potential_email:
        emails = [i for i in emails.split() if is_valid_email(i, at_symbol)]
        all_emails.update(emails)

    return all_emails


def remove_html_code(source_code_string, at_symbol):
    all_lines = set()
    for line in source_code_string.split("\n"):
        remove_start_stop = []

        if at_symbol not in line:
            continue

        for i in range(len(line)):
            if line[i] == "<":
                remove_start_stop.append(i)
            elif line[i] == ">":
                remove_start_stop.append(i)

        for i in range(len(remove_start_stop)-1,-1,-2):
            line = line[0:remove_start_stop[i-1]]+line[remove_start_stop[i]+1:]

        if at_symbol in line:
            all_lines.add(line)

    return all_lines


def find_links(source_code_string):
    all_links = set()
    for line in source_code_string.split("\n"):
        line = line.replace(" ", "")
        line = line.lower()
        if "<ahref=" not in line:
            continue

        line = line[line.find("<ahref=")+8:line.find('\"', line.find("<ahref=")+8)]

        all_links.add(line)
    return all_links


if __name__ == '__main__':
    print("Running")
    file = open("test.txt", "r", encoding="utf8")
    test = file.read()
    find_links(test)
    #print(*get_all_emails(test, "@"), sep="\n")
    #<a HREF="http://www.uu.se">Uppsala Universitet</a>

    print("Done runing")