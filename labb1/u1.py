# Labb 1 uppgift 1

def sum(num):
    sum = 0
    while num > 0:
        sum += num % 10
        num //= 10
    return sum

def testin_testing():
    print(sum(1234))
    print(sum(45331))
    print(sum(933422))
    print(sum(914324))


if __name__ == "__main__":
    #print(sum(914324))
    testin_testing()
