import urllib, socket, u4, urllib.request
from urllib.parse import urljoin


def make_complete_links(url):
    incomplete_links = u4.find_links(u4.get_source_code(url))
    complete_links = set()

    for i in incomplete_links:
        link = urljoin(url, i)
        complete_links.add(link)
    return complete_links


def check_link(links):
    working_links = []
    socket.setdefaulttimeout(10)
    for i in links:
        try:
            urllib.request.urlopen(i)
            print(i, "is a working link")
            working_links.append(i)
        except Exception as e:
            print(i, "is a dead link, ERROR message is:\n", e)
    return working_links


if __name__ == '__main__':
    #print(*make_complete_links("http://www.uu.se"), sep="\n")
    print(check_link(make_complete_links("http://www.uu.se")))