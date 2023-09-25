import random,urllib.request,ssl,string,validators

ssl._create_default_https_context = ssl._create_unverified_context
#url_without_emails = "https://user.it.uu.se/~joachim/"
url_with_emails = "https://www.math.uu.se/institutionen/personal/"


def get_source_code():
    file_object = urllib.request.urlopen(url_with_emails)
    content = file_object.read()
    text = content.decode('utf-8')
    return text


def is_valid_email(email_address):
    #En epost är en text sträng som innehåller ett namn följt av ett @ följt av en webbadress
    #Nu så säger vi att ett namn kan vara alla typer av texter som innehåller bara bokstäver, siffror och punkter
    #En webbadress säger vi nu är alla godkänd webbadresser så ett domännamn.toppdomän tex gmail.com
    try:
        email_address = email_address.split("@")
        print(email_address)
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


def find_first_email(text):
    at_index = text.find('@')
    if at_index < 0:
        return None
    name_start = find_name_start(text, at_index)
    if name_start == at_index:
        return None
    return text[name_start: at_index + 1]


def remove_html_code(source_code_string):
    all_lines = {str}
    for line in source_code_string.split("\n"):
        remove_start_stop = []

        if "@" not in line:
            continue

        for i in range(len(line)):
            if line[i] == "<":
                remove_start_stop.append(i)
            elif line[i] == ">":
                remove_start_stop.append(i)

        for i in range(len(remove_start_stop)-1,-1,-2):
            line = line[0:remove_start_stop[i-1]]+line[remove_start_stop[i]+1:]

        all_lines.add(line)

        """if "@" in line:
            all_lines.append(line)"""

    return all_lines



if __name__ == '__main__':
    print("Running")
    #I am testing this shit now
    #vilhelm.agdur<span class="rplcAt">@</span>math.uu.se</a>
    test = get_source_code()
    print(*remove_html_code(test), sep="\n")

    #print(is_valid_email("fredrik@ff2.se"))