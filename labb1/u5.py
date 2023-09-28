# Labb 1 Uppgift 5

import u4


def the_last_one():
    i = 1
    max_value, at_number = 0, 0
    while i <= 50:
        a = u4.length_of_u2(i)
        if a > max_value:
            max_value = a
            at_number = i
        print(str(i) + " has sequence length " + str(a))
        i += 1
    print("maximum at " + str(at_number) + " is " + str(max_value))


if __name__ == '__main__':
    the_last_one()
