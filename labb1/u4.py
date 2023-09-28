# Labb 1 Uppgift 4
import u2


def length_of_u2_v2(n):
    return len(u2.odd_or_even_v2(n))


def length_of_u2(n):
    count = 1
    while n != 1:
        n = u2.next_in_line(n)
        count += 1
    return count


if __name__ == '__main__':
    print(length_of_u2(6))
