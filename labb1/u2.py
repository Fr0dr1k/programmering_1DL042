# Labb 1 uppgift 2

def next_in_line(n):
    if n % 2 == 0:
        return int(n / 2)
    else:
        return n * 3 + 1


def odd_or_even(n):
    print(n)
    while n != 1:
        n = next_in_line(n)
        print(n)


def odd_or_even_v2(n):
    sequence = [n]
    while n != 1:
        n = next_in_line(n)
        sequence.append(n)
    return sequence


if __name__ == "__main__":
    odd_or_even(6)
