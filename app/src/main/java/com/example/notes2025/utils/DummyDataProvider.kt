package com.example.notes2025.utils

import com.example.notes2025.ui.feature.notelist.uimodel.SelectableNote

object DummyDataProvider {
    fun dummyNotesByAmount(amount: Int = 20): List<SelectableNote> =
        (1..amount).reversed().map {
            SelectableNote(
                title = "Note $it",
                contents = "Content $it",
            )
        }

    fun dummyNotes() =
        listOf(
            SelectableNote(
                title = "Note 1",
                contents =
                    "Last week, I had a business trip to Japan with my co-workers.\n" +
                        "It’s my first time travel to Japan so everything was amazing. \n" +
                        "We stayed there for 4 days. \u2028Luckily, the weather was always nice during the trip, sunny but not too hot.\n" +
                        "We spent the first 2 days working at the Shin-Yokohama office.\u2028The city was tidy and clean. You would definitely love the air here.\n" +
                        "On the third day, we travel to Tokyo by train.\n" +
                        "In contrast to Shin-Yokohama, Tokyo is a bustling, vibrant city with streets that are always full of people.\n" +
                        "During the day, we explored several famous locations.\n" +
                        "I’m especially impressed with the Sky Tree tower, where you can observe the entire city from above.\n" +
                        "At night, we took the trains back to the hotel and left Japan the next day.\n" +
                        "It was a wonderful experience, I would love to go back there again.",
            ),
            SelectableNote(
                title = "Note 2",
                contents =
                    "I’d like to tell you about a memorable birthday party I attended.\n" +
                        "It was one of my coworkers birthday, which took place last week.\n" +
                        "We celebrated it at a restaurant in Quang Binh during our company trip.\n" +
                        "There were around 10 people - all our team members.\n" +
                        "We enjoyed local dishes with unique local flavour and everyone is so drunk.\n" +
                        "Then we sang karaoke\ttogether. It’s the most memorable part because I’ve never sung in front of many people before.\n" +
                        "The atmosphere was lively and cheerful.\n" +
                        "Everyone had a great time laughing and enjoying themselves.",
            ),
            SelectableNote(
                title = "Note 3",
                contents =
                    "One of my favorite times of the year is summer vacation.\n" +
                        "It’s a time when I can relax and take a break from my usual routine.\n" +
                        "I usually take my vacations in July or August.\n" +
                        "In summer, it can be quite hot and humid, which makes swimming or going to the beach ideal.\n" +
                        "During this time, I often travel with my family or my coworkers to the seasides.\n" +
                        "I love the feeling of lying on the beach, reading a book and listening to the sound of the waves.\n" +
                        "It’s truly relaxing and enjoyable.\n" +
                        "I always look forward to this occasion every year.\n",
            ),
            SelectableNote(
                title = "Note 4",
                contents =
                    "Do you prefer traveling alone or with friends?\n" +
                        "I’d definitely choose traveling with friends over doing it alone\n" +
                        "Having your best friends around and do ",
            ),
            SelectableNote(
                title = "Note 5",
                contents =
                    "Would you rather visit popular tourist destinations or hidden, quiet places?\n" +
                        "I find most popular places are overrated. \n" +
                        "It’s not because of the places themselves, but because they usually attract a lot of people. \n" +
                        "I can’t enjoy anything in such a stuffy atmosphere.\n" +
                        "On the other hand, off-the-beaten-path places are much more interesting to me.\n" +
                        "I love traveling to places where I can truly feel and appreciate every moment.",
            ),
            SelectableNote(
                title = "Note 6",
                contents =
                    "Do you prefer fast food or traditional meals? \n" +
                        "Fast food is convenient? That’s true, but it’s surely unhealthy.\n" +
                        "As an athletic person, I prefer traditional meals with all the necessary nutrients rather than deep-fried fast food.\n" +
                        "In fact, I always advise people to avoid fast food to protect their health.",
            ),
            SelectableNote(
                title = "Note 7",
                contents = "Given the choice, I’d rather work at the office because I like seeing people.",
            ),
            SelectableNote(
                title = "Note 8",
                contents =
                    "D: Hi L! Long time no see. How have you been?\n" +
                        "\n" +
                        "L: Oh, hi D! I've been fine—everything's going well. How about you? How’s work?\n" +
                        "\n" +
                        "D: I'm good too, a little busy, but still doing well. Actually, I'm calling because I wanted to invite you to hang out this weekend. Are you free?\n" +
                        "\n" +
                        "L: Um... I guess I don’t have any plans yet. What do you have in mind?\n" +
                        "\n" +
                        "D: I heard there's a really good band playing at a teahouse in town this weekend. It sounds like fun, so I wanted to invite you. What do you think?\n" +
                        "\n" +
                        "L: Sounds interesting! What time are they playing?\n" +
                        "\n" +
                        "D: They’ll be playing at 8 p.m. this Sunday. I think it’ll be a nice way to relax after a long week. Would you like to come with me?\n" +
                        "\n" +
                        "L: Of course! I love listening to live music, especially in cozy places like teahouses. I’m really looking forward to it.\n" +
                        "\n" +
                        "D: Great! I’ll pick you up around 7:30 that evening so we can get there early and find some good seats.\n" +
                        "\n" +
                        "L: Perfect! I’ll be ready and waiting. Thanks for the invite.\n" +
                        "\n" +
                        "D: No problem. I’m really glad you can come. I’ll see you Sunday night. Have a great week!\n" +
                        "\n" +
                        "L: Thanks, D! You too. Bye, see you soon!\n" +
                        " \n" +
                        "D: Bye, L!",
            ),
            SelectableNote(
                title = "Note 9",
                contents =
                    "Last week, I had a business trip to Japan with my co-workers.\n" +
                        "It’s my first time travel to Japan so everything was amazing. \n" +
                        "We stayed there for 4 days. \u2028Luckily, the weather was always nice during the trip, sunny but not too hot.\n" +
                        "We spent the first 2 days working at the Shin-Yokohama office.\u2028The city was tidy and clean. You would definitely love the air here.\n" +
                        "On the third day, we travel to Tokyo by train.\n" +
                        "In contrast to Shin-Yokohama, Tokyo is a bustling, vibrant city with streets that are always full of people.\n" +
                        "During the day, we explored several famous locations.\n" +
                        "I’m especially impressed with the Sky Tree tower, where you can observe the entire city from above.\n" +
                        "At night, we took the trains back to the hotel and left Japan the next day.\n" +
                        "It was a wonderful experience, I would love to go back there again.",
            ),
            SelectableNote(
                title = "Note 10",
                contents =
                    "1. Luggage\n" +
                        "2. Departing\n" +
                        "3. Arriving\n" +
                        "4. Arriving\n" +
                        "5. Departing\n" +
                        "6. Luggage\n" +
                        "7. Delay\n" +
                        "8. Arriving",
            ),
            SelectableNote(
                title = "Note 11",
                contents = "Several years ago, a very strange thing happened to me.",
            ),
            SelectableNote(
                title = "Note 12",
                contents = "Several years ago, a very strange thing happened to me.",
            ),
        )
}
