import main as m


def test_refactor_text():
    expected_results = {"ett test är bra": "ett test är bra",
                        "ETt tEsT är brA": "ett test är bra",
                        "!??eTT    test ÄR BRA": "ett    test är bra",
                        "!(##": "",
                        "": "",
                        "A": "a"}
    print("Test of refactor_text()")
    count = 0
    passed_tests = 0
    for i in expected_results:
        count += 1
        print(str(count)+".\"" + m.refactor_text(i) + "\" should be \"" + expected_results[i]+"\"")
        if m.refactor_text(i) == expected_results[i]:
            passed_tests += 1
        else:
            print("TEST " + str(count) + " FAILED")
    if passed_tests == len(expected_results):
        print("All tested passed")
    else:
        print(len(expected_results)-passed_tests, "faild")


def test_make_list():
    expected_results = {"a test is good": ["a", "test", "is", "good"],
                        "A te3st IS the best": ["A", "te3st", "IS", "the", "best"],
                        "": [],
                        "    a good test  \n  is ": ["a", "good", "test", "is"]}
    print("Test of make_list()")
    count = 0
    passed_tests = 0
    for i in expected_results:
        count += 1
        print(str(count)+". The string \""+i+"\" makes the following ", m.make_list(i)," and should be",expected_results[i])
        if m.make_list(i) == expected_results[i]:
            passed_tests += 1
        else:
            print("TEST " + str(count) + " FAILED")
    if passed_tests == len(expected_results):
        print("All tested passed")
    else:
        print(len(expected_results)-passed_tests, "faild")

def test_unique_words():
    expected_results = {("a", "unique", "word", "test"): 4,
                        ("not", "only", "unique", "words", "not", "words", "only"): 4,
                        "": 0}
    print("Test of unique_word()")
    count = 0
    passed_tests = 0
    for i in expected_results:
        count += 1
        temp = m.make_dictionary(list(i))
        print(str(count) + ". The string \"",i ,"\" hase ", m.unique_words(temp), "unique words and should be",
              expected_results[i])
        if m.unique_words(temp) == expected_results[i]:
            passed_tests += 1
        else:
            print("TEST " + str(count) + " FAILED")
    if passed_tests == len(expected_results):
        print("All tested passed")
    else:
        print(len(expected_results) - passed_tests, "faild")


def test_number_of_words():
    expected_results = {"a test of this function": 5,
                        "a t e st of t 33": 7,
                        "": 0,
                        "        ": 0,
                        "  a          test\nof this": 4}
    print("Test number_of_words()")
    count = 0
    passed_tests = 0
    for i in expected_results:
        count += 1
        print(str(count)+". \""+i+"\" has",m.number_of_words(m.make_list(i)),"words")
        if m.number_of_words(m.make_list(i)) == expected_results[i]:
            passed_tests += 1
        else:
            print("TEST " + str(count) + " FAILED")
    if passed_tests == len(expected_results):
        print("All tested passed")
    else:
        print(len(expected_results) - passed_tests, "faild")


def test_make_dict():
    expected_results = {("a", "good", "test"): {"a": 1, "good": 1, "test": 1},
                        ("a", "good", "good", "good", "test", "test", "test", "test", "test"): {"a": 1, "good": 3, "test": 5},
                        ("a", "a", "a", "a", "a", "good", "good", "good", "test"): {"a": 5, "good": 3, "test": 1},
                        (): {}}
    print("Test of make_dict()")
    count = 0
    passed_tests = 0
    for i in expected_results:
        count += 1
        print(str(count) + ". The list", list(i), "makes the following", m.make_dictionary(list(i)), "dictionary and should be",
              expected_results[i])
        if m.make_dictionary(list(i)) == expected_results[i]:
            passed_tests += 1
        else:
            print("TEST " + str(count) + " FAILED")
    if passed_tests == len(expected_results):
        print("All tested passed")
    else:
        print(len(expected_results) - passed_tests, "FAILED")


def test_highest_key():
    expected_results = {("a", "good", "test"): "a",
                        ("a", "good", "good", "good", "test", "test", "test", "test", "test"): "test",
                        ("a", "a", "a", "a", "a", "good", "good", "good", "test"): "a",
                        "": None}
    print("Test of highest_key()")
    count = 0
    passed_tests = 0
    for i in expected_results:
        count += 1
        print(str(count) + ". The dictionary", m.make_dictionary(list(i)), "highest key is ",
              m.highest_key(m.make_dictionary(list(i))), "and should be", expected_results[i])
        if m.highest_key(m.make_dictionary(list(i))) == expected_results[i]:
            passed_tests += 1
        else:
            print("TEST " + str(count) + " FAILED")
    if passed_tests == len(expected_results):
        print("All tested passed")
    else:
        print(len(expected_results) - passed_tests, "FAILED")


def test_dictionary_of_following_words():
    expected_results = {("a", "test", "of", "my", "code"): {"a": ["test"], "test": ["of"], "of": ["my"], "my": ["code"]},
                        ("du", "och", "du", "och", "valfrid"): {"du": ["och", "och"], "och": ["du", "valfrid"]},
                        (): {}}

    print("Test of dictionary_of_following_words()")
    count = 0
    passed_tests = 0
    for i in expected_results:
        count += 1
        print(str(count)+".", list(i),"makes", m.dictionary_of_following_words(list(i)), "and should be",
              expected_results[i])
        if m.dictionary_of_following_words(list(i)) == expected_results[i]:
            passed_tests += 1
        else:
            print("TEST " + str(count) + " FAILED")
    if passed_tests == len(expected_results):
        print("All tested passed")
    else:
        print(len(expected_results) - passed_tests, "FAILED")


def test_most_common_following_word():
    expected_results = {("a", "test", "of", "my", "code"): "test",
                        ("du", "och", "du", "och", "valfrid"): "och",
                        (): None}

    count = 0
    passed_tests = 0
    for i in expected_results:
        count += 1
        temp = m.dictionary_of_following_words(list(i))
        print(str(count) + ".", temp, "makes \""+str(m.most_common_following_word(temp)) +"\" and should be \"" +
              str(expected_results[i])+ "\"")
        if m.most_common_following_word(temp) == expected_results[i]:
            passed_tests += 1
        else:
            print("TEST " + str(count) + " FAILED")
    if passed_tests == len(expected_results):
        print("All tested passed")
    else:
        print(len(expected_results) - passed_tests, "FAILED")


if __name__ == '__main__':
    test_number_of_words()
    test_unique_words()
    test_highest_key()