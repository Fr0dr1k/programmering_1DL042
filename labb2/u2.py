def initials(name):
    return '.'.join(i[0].upper() for i in name.split())


def testing_testing(names):
    for i in names:
        print(i, " har initialerna ", initials(i))


if __name__ == '__main__':
    testing_testing(["Fredrik Forsgard", "!joachim Gunnar Parrow", "Lucas Enroth", "", "f", "Charl-Jonson Andersson"])
