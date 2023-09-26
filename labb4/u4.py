import random, urllib.request, ssl, string, pickle


def get_source_code(url):
    file_object = urllib.request.urlopen(url)
    content = file_object.read()
    text = content.decode('utf-8')

    print("Data collected")
    return text


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


def find_links_from_url(url):
    content = get_source_code(url)
    all_links = find_links(content)

    for i in all_links:
        print(i)

if __name__ == '__main__':
    find_links_from_url("http://www.uu.se")
    print("Done")