# Labb 1 uppgift 3

import u1


def les_then_10000(start, end):
    for i in range(start, end):
        if u1.sum(i) ** 3 == i:
            print(i)


def second_version():
    i = 0
    while i < 10000:
        i += 1
        if u1.sum(i) ** 3 == i:
            print(i)


if __name__ == '__main__':
    les_then_10000(1, 10001)
    # second_version()
