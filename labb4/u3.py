import random,urllib.request,ssl,string,pickle

ssl._create_default_https_context = ssl._create_unverified_context
#url_without_emails = "https://user.it.uu.se/~joachim/"
url_with_emails = "https://www.math.uu.se/institutionen/personal/"

EMAIL_CHARS = string.ascii_letters + string.digits + '.'

def get_source_code(url):
    file_object = urllib.request.urlopen(url)
    content = file_object.read()
    text = content.decode('utf-8')

    return text


def is_valid_email(email_address, at_symbol):
    #En epost är en text sträng som innehåller ett namn följt av ett @ följt av en webbadress
    #Nu så säger vi att ett namn kan vara alla typer av texter som innehåller bara bokstäver, siffror och punkter
    #En webbadress säger vi nu är alla godkänd webbadresser så ett domännamn.toppdomän tex gmail.com där
    #domännamn får inehåla bokstäver sifror och punkt och toppdomänen är en godkänns toppdomän t ex .com eller .se
    #Domännamnet kan inte heller ha flerla punkter i rad t ex fredrik@ff2...se är inte godtjänd
    try:
        with open("top_domains.txt", "rb") as file:
            valid_top_domains = pickle.load(file)

        email_address = email_address.split(at_symbol)
        if not len(email_address) == 2:
            return False

        if len(email_address[0]) < 1 or len(email_address[1]) < 1:
            return False

        if "." not in email_address[1]:
            return False

        if ".." in email_address[1]:
            return False

        if email_address[1][email_address[1].rfind(".")+1:].upper() not in valid_top_domains:
            return False

        if len(email_address[1][:email_address[1].rfind(".")]) < 1:
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


def find_name_start(text, at_index):
    first_index = 0
    for index in range(at_index-1, -1, -1): #baklänges!
        if text[index] not in EMAIL_CHARS:

            first_index = index + 1
            break
    return first_index


def find_email_end(text, at_index):
    last_index = len(text)
    for i in range(at_index+1, len(text)-1):
        if text[i] not in EMAIL_CHARS:
            last_index = i
            break
    return last_index


def get_all_emails(source_code_string, at_symbol):
    try:
        all_potential_email = remove_html_code(source_code_string, at_symbol)
        all_emails = set()

        for emails in all_potential_email:
            #emails = [i for i in emails.split() if is_valid_email(i, at_symbol)]
            while at_symbol in emails:
                at_index = emails.find(at_symbol)
                start_index = find_name_start(emails,at_index)
                end_index = find_email_end(emails,at_index)
                new_email = emails[start_index:end_index]
                emails = emails[end_index:]

                if is_valid_email(new_email,at_symbol):
                    all_emails.add(new_email)

        return all_emails
    except:
        print("Something is wrong I can feel it -Eminem")


def remove_html_code(source_code_string, at_symbol):
    all_lines = set()

    for line in source_code_string.split("\n"):
        remove_start_stop = []

        if at_symbol not in line:
            continue

        if "mailto:" in line:
            index_of_mailto = line.find("mailto:")
            all_lines.add(line[index_of_mailto:line.find("\"", index_of_mailto)])

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


def emails_from_url(url):
    content = get_source_code(url)
    all_emails = get_all_emails(content,"@")

    for i in all_emails:
        print(i)

if __name__ == '__main__':
    print("Running")
    file = open("test.txt", "r", encoding="utf8")
    test = file.read()
    #find_links(test)
    #print(*get_all_emails(test, "@"), sep="\n")
    #print(get_all_emails("\\ntest@hej.se","@"))
    #print(get_all_emails(1,"@"))
    #print(get_all_emails(get_source_code("http://www.it.uu.se/katalog/bylastname"),"@"))
    #<a HREF="http://www.uu.se">Uppsala Universitet</a>
    #print(is_valid_email("fredrik@ff2.se","@"))
    print(get_all_emails(test,"@"))

    print("Done running")
