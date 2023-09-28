def is_sorted(number_list):
    for i in range(len(number_list) - 1):
        if number_list[i] > number_list[i + 1]:
            return False
    return True


def testing_testing(list_of_list):
    for i in list_of_list:
        if is_sorted(i):
            print(i, " är sorterad")
        else:
            print(i, " är inte sorterad")


if __name__ == '__main__':
    # print(is_sorted([1,30,332,43231,53213131]))
    testing_testing([[1, 2, 3, 4], [1], [1, 543, 123, 312312], [1, 1, 1, 3, 4], [], [-30, 0, 30]])
