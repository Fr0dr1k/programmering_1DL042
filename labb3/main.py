import random


def read_file(file_path):
    text_file = open(file_path, "r", encoding='utf8')
    text = text_file.read()
    return text


def refactor_text(text):
    text = "".join(i for i in text.lower() if i.isalpha() or i == " " or i == "\n")
    return text


def make_list(text):
    return text.split()


def number_of_words(text_list):
    return len(text_list)


def unique_words(text_dict):
    assert type(text_dict) is dict, "text_dict needs to be a dictionary"
    return len(text_dict)


def make_dictionary(text_list):
    assert type(text_list) is list, "text_list needs to be a list of text"
    text_dict = {}
    for i in text_list:
        if i in text_dict:
            text_dict[i] += 1
        else:
            text_dict[i] = 1
    return text_dict


def highest_key(word_dict):
    assert type(word_dict) is dict, "word_dict needs to be a dictionary of words"

    """if(len(word_dict)>0):
        max_key = max(word_dict, key=word_dict.get)
        return max_key
    else:
        return None"""

    if len(word_dict) > 0:
        max_key = next(iter(word_dict))
        for i in word_dict:
            if word_dict[i] > word_dict[max_key]:
                max_key = i
        return max_key

    else:
        return None


def dictionary_of_following_words(text_list):
    assert type(text_list) is list, "text_list needs to be a list"

    dictionary = {}

    for i in range(len(text_list) - 1):
        if text_list[i] in dictionary:
            dictionary[text_list[i]].append(text_list[i + 1])
        else:
            dictionary[text_list[i]] = [text_list[i + 1]]
    return dictionary


def most_common_following_word(word_dict):
    assert type(word_dict) is dict, "word_dict needs to be a dictionary of words"

    if len(word_dict) > 0:
        """most_common = next(iter(word_dict))

        for i in word_dict:
            print(i)
            if len(word_dict[i]) > len(word_dict[most_common]):
                most_common = i
        dict_of_common = make_dictionary(word_dict[most_common])"""

        most_common = max(word_dict.values(), key=len)
        dict_of_common = make_dictionary(most_common)

        most_following_word = max(dict_of_common, key=dict_of_common.get)
        return most_following_word
    else:
        return None


def split_only_whitespace(text):
    for i in range(len(text)-1, -1, -1):
        if text[i] == "\n":
            text = text[0:i+1] + " " + text[i+1:]

    text = text.split(" ")
    for i in range(len(text)-1, -1, -1):
        if not text[i].split():
            text.pop(i)

    return text


def random_text_generator(path_to_text, text_length):
    text = read_file(path_to_text)
    text = text.split(" ")

    last_word = text[-1]

    text = dictionary_of_following_words(text)


    #current_word = random.choice(list(text.keys()))

    current_word = ""
    for i in range(len(text)):
        current_word = random.choice(list(text.keys()))
        if current_word[0].isupper():
            break

    random_text = current_word

    for i in range(text_length-1):
        times_looped = 0

        while True:
            times_looped += 1

            new_current_word = random.choice(text[current_word])
            if new_current_word != last_word or i >= text_length-1:
                current_word = new_current_word
                break

            elif times_looped == len(text[current_word]):
                random_text += " "+last_word
                return random_text

        random_text += " "+current_word
    return random_text


if __name__ == '__main__':
    """test = read_file("text.txt")
    print("1")
    test = refactor_text(test)
    print("2")
    test = make_list(test)
    print("3")
    test = dictionary_of_following_words(test)
    print(test)
    test = most_common_following_word(test)
    print(test)"""

    test = random_text_generator("HP.txt", 100)
    print(test)