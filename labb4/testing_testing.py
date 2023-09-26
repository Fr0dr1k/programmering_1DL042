import u3

def test_get_mail_from_string():
    expected_results = {"test luckass@gmail.com test ": {"luckass@gmail.com"},
                        "fredrik@ff2.se": {"fredrik@ff2.se"},
                        "test@": set(),
                        "mailto:fredrik@ff2.se": {"fredrik@ff2.se"},
                        "t    e££@öööö.#\n#": set(),
                        "fredrik@ff2.aqv greger@test.se": {"greger@test.se"},
                        "hej@test.com  @ ## £$@$ test@jag.se": {"hej@test.com", "test@jag.se"}}

    count = 0
    passed = 0
    for i in expected_results:
        count += 1
        print(str(count)+".", u3.get_all_emails(i,"@"), "should be",expected_results[i])
        if u3.get_all_emails(i,"@")==expected_results[i]:
            passed += 1
    print(str(passed)+"/"+str(len(expected_results))+" tests passed")


def test_get_mail_from_url():
    urls = ["http://www.it.uu.se/katalog/bylastname", "https://www.dn.se/kontakt/", "https://www.math.uu.se/institutionen/personal/",
            "https://user.it.uu.se/~joachim/"]

    for i in urls:
        print("The email fond at "+i+" are")
        u3.emails_from_url(i)
        print("------------------------------")



