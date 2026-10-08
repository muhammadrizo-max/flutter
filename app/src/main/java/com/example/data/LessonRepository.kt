package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.*

class LessonRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("flutter_quest_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_AVATAR = "avatar"
        private const val KEY_XP = "xp"
        private const val KEY_LEVEL = "level"
        private const val KEY_COINS = "coins"
        private const val KEY_HEARTS = "hearts"
        private const val KEY_STREAK = "streak"
        private const val KEY_LAST_DAILY = "last_daily"
        private const val KEY_IS_PRO = "is_pro"
        private const val KEY_COMPLETED_LESSONS = "completed_lessons"
        private const val KEY_UNLOCKED_LESSONS = "unlocked_lessons"
        private const val KEY_CLAIMED_ACHIEVEMENTS = "claimed_achievements"
        private const val KEY_COMPLETED_CHALLENGES = "completed_challenges"
        private const val KEY_QUIZ_CORRECT = "quiz_correct"
        private const val KEY_QUIZ_TOTAL = "quiz_total"
        private const val KEY_DARK_MODE = "dark_mode_pref"
    }

    val allLessons: List<Lesson> = listOf(
        // LEVEL 1
        Lesson(
            id = "level_1",
            moduleNumber = 1,
            moduleTitle = "1-Bosqich: Dart & Flutter Poydevori",
            orderNumber = 1,
            title = "Level 1: Kirish, Ishchi Muhit va Flutter SDK",
            subtitle = "Flutter nima, Dart tili va birinchi dastur",
            iconName = "rocket_launch",
            isFree = true,
            unlockCost = 0,
            xpReward = 100,
            coinReward = 30,
            estimatedMinutes = 5,
            theorySections = listOf(
                TheorySection(
                    title = "Flutter va Ishchi Muhit nima?",
                    content = "Flutter - Google tomonidan yaratilgan ochiq kodli kross-platforma UI toolkitdir. U bitta Dart kodi bazasi orqali Android, iOS, Web va Desktop uchun yuqori unumdorlikka ega ilovalar yaratish imkonini beradi. AOT (Ahead-Of-Time) kompilyatsiyasi sababli Flutter ilovalari juda tez ishlaydi va o'zining mustaqil Skia/Impeller rendering dvigateliga ega. Muhitni sozlash uchun Flutter SDK, Git, Android Studio hamda VS Code o'rnatiladi. Har bir Flutter loyihasining kirish nuqtasi main.dart faylidagi void main() va runApp() hisoblanadi.",
                    codeSnippet = "import 'package:flutter/material.dart';\n\nvoid main() {\n  runApp(\n    const MaterialApp(\n      home: Scaffold(\n        body: Center(\n          child: Text('Salom, Flutter!'),\n        ),\n      ),\n    ),\n  );\n}",
                    flutterTip = "Hot Reload xususiyati tufayli kodni o'zgartirgach, ilovani qayta o'rnatmasdan natijani 1 soniyada ko'rasiz!"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q1_1",
                    question = "Flutter loyihalarida kod qaysi fayl va funksiyadan ishga tushadi?",
                    options = listOf("main.dart va void main()", "app.dart va start()", "index.html va init()", "build.dart va render()"),
                    correctIndex = 0,
                    explanation = "Har qanday Flutter ilovasi main.dart faylidagi void main() funksiyasidan ishga tushadi."
                ),
                QuizQuestion(
                    id = "q1_2",
                    question = "Kodni saqlagandayoq ekrandagi o'zgarishni darhol ko'rsatuvchi xususiyat nima deyiladi?",
                    options = listOf("AOT Compilation", "Hot Reload", "Build Mode", "Gradle Sync"),
                    correctIndex = 1,
                    explanation = "Hot Reload Flutterning eng kuchli xususiyati bo'lib, UI-ni darhol yangilaydi."
                ),
                QuizQuestion(
                    id = "q1_3",
                    question = "Flutter ilovalari asosan qaysi dasturlash tilida yoziladi?",
                    options = listOf("Java", "Python", "Dart", "Swift"),
                    correctIndex = 2,
                    explanation = "Flutter Google tomonidan ishlab chiqilgan zamonaviy Dart dasturlash tilida yoziladi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c1",
                title = "Birinchi print() buyrug'i",
                instruction = "Birinchi Dart dasturida ekranga matn chiqarish uchun bo'sh joyga print yozing:",
                startingCode = "void main() {\n  // TODO: print orqali matn chiqaring\n  print(\"Salom, Flutter!\");\n}",
                solutionCode = "void main() {\n  print(\"Salom, Flutter!\");\n}",
                requiredKeywords = listOf("print", "Flutter"),
                widgetType = SimulatedWidgetType.TEXT_ONLY,
                hint = "print(\"Salom, Flutter!\"); deb yozing",
                initialPreviewParam = "Salom, Flutter!"
            )
        ),

        // LEVEL 2
        Lesson(
            id = "level_2",
            moduleNumber = 1,
            moduleTitle = "1-Bosqich: Dart & Flutter Poydevori",
            orderNumber = 2,
            title = "Level 2: Dart Dasturlash Tili Asoslari",
            subtitle = "Sintaksis, o'zgaruvchilar va ma'lumot turlari",
            iconName = "code",
            isFree = true,
            unlockCost = 0,
            xpReward = 150,
            coinReward = 35,
            estimatedMinutes = 5,
            theorySections = listOf(
                TheorySection(
                    title = "Dart Asosiy Sintaksisi",
                    content = "Dart — obyektga yo'naltirilgan, statik tipizatsiyalangan til. Asosiy ma'lumot turlari: String (matn), int (butun son), double (haqiqiy son), bool (mantiqiy qiymat). O'zgaruvchilarni e'lon qilishda var yoki aniq tip ko'rsatiladi. O'zgarmas qiymatlar uchun const (kompilyatsiya vaqtida aniq) va final (ishga tushish vaqtida aniq) ishlatiladi. String interpolyatsiyasi \$ism yoki \${expression} ko'rinishida matn ichiga qiymat joylashga imkon beradi.",
                    codeSnippet = "void main() {\n  String ism = 'Bobur';\n  int yosh = 22;\n  double ball = 95.5;\n  final bool talabami = true;\n  print('Talaba \$ism, Yoshi: \$yosh, Balli: \$ball');\n}",
                    flutterTip = "Matn ichida o'zgaruvchi qiymatini \$ belgisi bilan chiqarish interpolyatsiya deyiladi."
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q2_1",
                    question = "Dart'da matn ichiga o'zgaruvchi qiymatini joylashtirish (interpolyatsiya) qaysi belgi bilan qilinadi?",
                    options = listOf("#", "$", "@", "&"),
                    correctIndex = 1,
                    explanation = "Dart string interpolyatsiyasida \$ belgisidan foydalaniladi (masalan: 'Salom, \$ism')."
                ),
                QuizQuestion(
                    id = "q2_2",
                    question = "Kompilyatsiya vaqtida qat'iy va o'zgarmas hisoblanadigan o'zgaruvchi qaysi?",
                    options = listOf("var", "dynamic", "const", "late"),
                    correctIndex = 2,
                    explanation = "const qiymati dastur kompilyatsiya qilinayotgandayoq ma'lum bo'lgan qat'iy konstantadir."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c2",
                title = "O'zgaruvchilar va Interpolyatsiya",
                instruction = "String ism = 'Flutter'; deb e'lon qiling va print orqali ekranga chiqaring:",
                startingCode = "void main() {\n  String ism = 'Flutter';\n  print('Salom, \$ism!');\n}",
                solutionCode = "void main() {\n  String ism = 'Flutter';\n  print('Salom, \$ism!');\n}",
                requiredKeywords = listOf("String", "ism", "print"),
                widgetType = SimulatedWidgetType.TEXT_ONLY,
                hint = "String ism = 'Flutter'; print('Salom, \$ism!');",
                initialPreviewParam = "Salom, Flutter!"
            )
        ),

        // LEVEL 3
        Lesson(
            id = "level_3",
            moduleNumber = 1,
            moduleTitle = "1-Bosqich: Dart & Flutter Poydevori",
            orderNumber = 3,
            title = "Level 3: Mantiqiy Boshqaruv va Sikllar",
            subtitle = "Control Flow, if-else va for/while sikllari",
            iconName = "alt_route",
            isFree = true,
            unlockCost = 0,
            xpReward = 200,
            coinReward = 40,
            estimatedMinutes = 6,
            theorySections = listOf(
                TheorySection(
                    title = "Shart Operatorlari va Sikllar",
                    content = "Dastur oqimini boshqarish uchun if, else if, else shart operatorlari hamda switch-case ishlatiladi. Takrorlanuvchi amallarni bajarish uchun for, while, do-while sikllari qo'llaniladi. Sikllarda break (siklni to'xtatish) va continue (keyingi iteratsiyaga o'tish) buyruqlaridan foydalaniladi.",
                    codeSnippet = "void main() {\n  int ball = 85;\n  if (ball >= 90) {\n    print('A');\n  } else if (ball >= 80) {\n    print('B');\n  } else {\n    print('C');\n  }\n  for (int i = 1; i <= 3; i++) {\n    print('Sikl: \$i');\n  }\n}",
                    flutterTip = "break siklni butunlay to'xtatadi, continue esa faqat joriy qadamni o'tkazib yuboradi."
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q3_1",
                    question = "Siklni muddatidan oldin butunlay to'xtatish uchun qaysi kalit so'z ishlatiladi?",
                    options = listOf("exit", "stop", "break", "continue"),
                    correctIndex = 2,
                    explanation = "break operatori siklni darhol yakunlaydi va undan chiqib ketadi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c3",
                title = "Shart tekshiruvi (if)",
                instruction = "yosh >= 18 bo'lganda 'Ruxsat berildi' deb chiqaruvchi if shartini yozing:",
                startingCode = "void main() {\n  var yosh = 18;\n  if (yosh >= 18) {\n    print('Ruxsat berildi');\n  }\n}",
                solutionCode = "void main() {\n  var yosh = 18;\n  if (yosh >= 18) {\n    print('Ruxsat berildi');\n  }\n}",
                requiredKeywords = listOf("if", "yosh", "print"),
                widgetType = SimulatedWidgetType.TEXT_ONLY,
                hint = "if (yosh >= 18) { print('Ruxsat berildi'); }",
                initialPreviewParam = "Ruxsat berildi"
            )
        ),

        // LEVEL 4
        Lesson(
            id = "level_4",
            moduleNumber = 2,
            moduleTitle = "2-Bosqich: Kolleksiyalar va OOP",
            orderNumber = 4,
            title = "Level 4: Kolleksiyalar va Ma'lumotlar Tuzilmasi",
            subtitle = "List, Map, Set to'plamlari bilan ishlash",
            iconName = "dataset",
            isFree = false,
            unlockCost = 40,
            xpReward = 250,
            coinReward = 45,
            estimatedMinutes = 6,
            theorySections = listOf(
                TheorySection(
                    title = "List, Map va Set Kolleksiyalari",
                    content = "Dart'da eng ko'p qo'llaniladigan kolleksiyalar: List - tartiblangan elementlar ro'yxati [1, 2, 3]. Map - kalit va qiymat juftligi {'ism': 'Ali', 'yosh': 20}. Set - takrorlanmas, unikal elementlar to'plami {1, 2, 3}. Generics (masalan, List<String>) orqali tip xavfsizligi ta'minlanadi.",
                    codeSnippet = "void main() {\n  List<String> mevalar = ['Olma', 'Banan', 'Anor'];\n  mevalar.add('Shaftoli');\n  Map<String, dynamic> foyl = {'ism': 'Sardor', 'yosh': 25};\n  print('Meva: \${mevalar[0]}, Foydalanuvchi: \${foyl['ism']}');\n}",
                    flutterTip = "List indekslari doimo 0 dan boshlanadi!"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q4_1",
                    question = "Kalit va qiymat (Key-Value) juftligi ko'rinishidagi kolleksiya qaysi?",
                    options = listOf("List", "Set", "Map", "Array"),
                    correctIndex = 2,
                    explanation = "Map kolleksiyasi har bir qiymatga uning maxsus kaliti (key) orqali murojaat qilishni ta'minlaydi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c4",
                title = "List e'lon qilish",
                instruction = "List<String> elementlarini e'lon qilib ekranga chiqaring:",
                startingCode = "void main() {\n  List<String> rovxat = ['Dart', 'Flutter'];\n  print(rovxat[0]);\n}",
                solutionCode = "void main() {\n  List<String> rovxat = ['Dart', 'Flutter'];\n  print(rovxat[0]);\n}",
                requiredKeywords = listOf("List", "rovxat", "print"),
                widgetType = SimulatedWidgetType.TEXT_ONLY,
                hint = "List<String> rovxat = ['Dart', 'Flutter'];",
                initialPreviewParam = "Dart"
            )
        ),

        // LEVEL 5
        Lesson(
            id = "level_5",
            moduleNumber = 2,
            moduleTitle = "2-Bosqich: Kolleksiyalar va OOP",
            orderNumber = 5,
            title = "Level 5: Funksiyalar va Asinxron Dasturlash",
            subtitle = "Future, async va await operatorlari",
            iconName = "hourglass_empty",
            isFree = false,
            unlockCost = 45,
            xpReward = 300,
            coinReward = 50,
            estimatedMinutes = 6,
            theorySections = listOf(
                TheorySection(
                    title = "Asinxron Dasturlash (async / await)",
                    content = "Funksiyalar kodni qayta ishlatish imkonini beradi. Dart'da pozitsion va nomlangan (named parameters) parametrlar mavjud. Ixtiyoriy parametrlar {} yoki [] qavslar ichida beriladi. Asinxron amallar (fayl o'qish, API ga so'rov yuborish) vaqt oladi. Ularni kutish uchun Future, async va await operatorlari ishlatiladi.",
                    codeSnippet = "Future<String> malumotOlish() async {\n  await Future.delayed(const Duration(seconds: 2));\n  return 'Ma\\'lumot yuklandi';\n}\n\nvoid main() async {\n  print('Kutilmoqda...');\n  String natija = await malumotOlish();\n  print(natija);\n}"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q5_1",
                    question = "Asinxron funksiyada natijani kutib olish uchun qaysi operator ishlatiladi?",
                    options = listOf("wait", "await", "then", "defer"),
                    correctIndex = 1,
                    explanation = "'await' operatori asinxron operatsiya (Future) natijasi tayyor bo'lishini kutadi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c5",
                title = "Asinxron funksiya",
                instruction = "Future bilan ishlovchi async funksiyada await operatorini yozing:",
                startingCode = "Future<void> yuklash() async {\n  await Future.delayed(Duration(seconds: 1));\n  print('Bajarildi!');\n}",
                solutionCode = "Future<void> yuklash() async {\n  await Future.delayed(Duration(seconds: 1));\n  print('Bajarildi!');\n}",
                requiredKeywords = listOf("Future", "async", "await"),
                widgetType = SimulatedWidgetType.TEXT_ONLY,
                hint = "await Future.delayed(...);",
                initialPreviewParam = "Bajarildi!"
            )
        ),

        // LEVEL 6
        Lesson(
            id = "level_6",
            moduleNumber = 2,
            moduleTitle = "2-Bosqich: Kolleksiyalar va OOP",
            orderNumber = 6,
            title = "Level 6: Obyektga Yo'naltirilgan Dasturlash (OOP Asoslari)",
            subtitle = "Class, Object, Konstruktorlar va Inkapsulyatsiya",
            iconName = "category",
            isFree = false,
            unlockCost = 50,
            xpReward = 350,
            coinReward = 50,
            estimatedMinutes = 7,
            theorySections = listOf(
                TheorySection(
                    title = "Class va Object Tushunchalari",
                    content = "OOP real dunyodagi obyektlarni kodda modellashtirish imkonini beradi. Class (sinf) - obyekt yaratish uchun shablon, Object - sinfning aniq ekzemplyari. Konstruktorlar obyekt yaratilayotganda qiymatlarni boshlang'ich holatga keltirish uchun ishlatiladi. Inkapsulyatsiya xususiyati orqali o'zgaruvchilar oldiga _ qo'yilib, private qilinadi.",
                    codeSnippet = "class Mashina {\n  String nomi;\n  int yili;\n  Mashina({required this.nomi, required this.yili});\n  void haydash() => print('\$nomi harakatlanmoqda');\n}\n\nvoid main() {\n  var m = Mashina(nomi: 'Cobalt', yili: 2023);\n  m.haydash();\n}"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q6_1",
                    question = "Dart tilida o'zgaruvchi yoki metodni private qilish uchun uning nomi oldidan qaysi belgi qo'yiladi?",
                    options = listOf("#", "private", "_ (pastki chiziq)", "$"),
                    correctIndex = 2,
                    explanation = "Dart tilida nom oldidan pastki chiziq (_) qo'yilishi uni ushbu fayl/kutubxona doirasida xususiy (private) qiladi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c6",
                title = "Sinf e'lon qilish",
                instruction = "class Car sinfini e'lon qilib model nomini saqlang:",
                startingCode = "class Car {\n  String model;\n  Car(this.model);\n}",
                solutionCode = "class Car {\n  String model;\n  Car(this.model);\n}",
                requiredKeywords = listOf("class", "Car", "model"),
                widgetType = SimulatedWidgetType.CONTAINER_TEXT,
                hint = "class Car { String model; Car(this.model); }",
                initialPreviewParam = "Car: Cobalt"
            )
        ),

        // LEVEL 7
        Lesson(
            id = "level_7",
            moduleNumber = 2,
            moduleTitle = "2-Bosqich: Kolleksiyalar va OOP",
            orderNumber = 7,
            title = "Level 7: Chuqurlashtirilgan OOP & Null Safety",
            subtitle = "Vorislik (extends), Abstraksiya, Mixin va Null Safety",
            iconName = "shield",
            isFree = false,
            unlockCost = 55,
            xpReward = 400,
            coinReward = 55,
            estimatedMinutes = 7,
            theorySections = listOf(
                TheorySection(
                    title = "Vorislik va Null Safety",
                    content = "Vorislik (extends) mavjud sinf xususiyatlarini yangi sinfga o'tkazadi. Abstrakt sinflar (abstract class) faqat shablon vazifasini o'taydi va ulardan to'g'ridan-to'g'ri obyekt olinmaydi. Mixinlar (with) ko'p marotaba ishlatiladigan xatti-harakatlarni ko'chirishga yordam beradi. Null Safety dasturda null pointer xatolarining oldini oladi (non-nullable by default, ? va ! operatorlari).",
                    codeSnippet = "abstract class Hayvon {\n  void ovozChiqar();\n}\n\nclass Kuchuk extends Hayvon {\n  @override\n  void ovozChiqar() => print('Vov-vov');\n}\n\nvoid main() {\n  String? ism = null; // Nullable\n  Hayvon h = Kuchuk();\n  h.ovozChiqar();\n}"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q7_1",
                    question = "Sinf boshqa bir sinfdan voris olishi uchun qaysi kalit so'z ishlatiladi?",
                    options = listOf("implements", "extends", "with", "super"),
                    correctIndex = 1,
                    explanation = "extends kalit so'zi ota sinfning barcha ochiq metod va parametrlarini voris sinfga beradi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c7",
                title = "Vorislik kodi (extends)",
                instruction = "extends Hayvon orqali yangi sinf yarating:",
                startingCode = "class Kuchuk extends Hayvon {\n  @override\n  void ovoz() => print('Vov');\n}",
                solutionCode = "class Kuchuk extends Hayvon {\n  @override\n  void ovoz() => print('Vov');\n}",
                requiredKeywords = listOf("class", "extends", "Hayvon"),
                widgetType = SimulatedWidgetType.CONTAINER_TEXT,
                hint = "class Kuchuk extends Hayvon",
                initialPreviewParam = "Kuchuk: Vov"
            )
        ),

        // LEVEL 8
        Lesson(
            id = "level_8",
            moduleNumber = 3,
            moduleTitle = "3-Bosqich: Flutter UI & Vidjetlar Olami",
            orderNumber = 8,
            title = "Level 8: Flutter Widget Tizimi va Scaffold",
            subtitle = "StatelessWidget vs StatefulWidget va ekran skeleti",
            iconName = "dashboard",
            isFree = false,
            unlockCost = 60,
            xpReward = 450,
            coinReward = 60,
            estimatedMinutes = 7,
            theorySections = listOf(
                TheorySection(
                    title = "Widgetlar va Scaffold",
                    content = "Flutter'da barcha narsa Widget hisoblanadi. StatelessWidget - ichki holati o'zgarmaydigan widget. StatefulWidget - foydalanuvchi harakatiga ko'ra o'zgaruvchan holatga (State) ega widget. Scaffold - ekran strukturasi (appBar, body, floatingActionButton, bottomNavigationBar, drawer). Asosiy widgetlar: Text, Image, Icon, ElevatedButton, TextButton.",
                    codeSnippet = "class HelloWidget extends StatelessWidget {\n  const HelloWidget({super.key});\n  @override\n  Widget build(BuildContext context) {\n    return Scaffold(\n      appBar: AppBar(title: const Text('Bosh Sahifa')),\n      body: const Center(child: Text('Stateless Widget')),\n    );\n  }\n}"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q8_1",
                    question = "Flutter loyihada ekranning standart maketi va qobig'ini yaratib beruvchi widget qaysi?",
                    options = listOf("Container", "Scaffold", "MaterialApp", "Column"),
                    correctIndex = 1,
                    explanation = "Scaffold ekranning asosiy vizual maketini (AppBar, Body, FAB, Drawer) ta'minlaydi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c8",
                title = "Scaffold yaratish",
                instruction = "Scaffold ichida AppBar va body qaytaring:",
                startingCode = "@override\nWidget build(BuildContext context) {\n  return Scaffold(\n    body: Center(child: Text('Flutter UI')),\n  );\n}",
                solutionCode = "@override\nWidget build(BuildContext context) {\n  return Scaffold(\n    body: Center(child: Text('Flutter UI')),\n  );\n}",
                requiredKeywords = listOf("Scaffold", "body", "Center", "Text"),
                widgetType = SimulatedWidgetType.APP_BAR_SCAFFOLD,
                hint = "return Scaffold(body: Center(child: Text('Flutter UI')));",
                initialPreviewParam = "Flutter UI"
            )
        ),

        // LEVEL 9
        Lesson(
            id = "level_9",
            moduleNumber = 3,
            moduleTitle = "3-Bosqich: Flutter UI & Vidjetlar Olami",
            orderNumber = 9,
            title = "Level 9: Layouts, Responsive UI va Styling",
            subtitle = "Row, Column, Expanded, Container va moslashuvchanlik",
            iconName = "view_quilt",
            isFree = false,
            unlockCost = 65,
            xpReward = 500,
            coinReward = 60,
            estimatedMinutes = 8,
            theorySections = listOf(
                TheorySection(
                    title = "Joylashuv Vidjetlari",
                    content = "Elementlarni joylashtirish uchun Row (gorizontal) va Column (vertikal) ishlatiladi. Container - o'lcham, chekinish (margin, padding), rang va chegaralarni berish uchun ishlatiladi. Expanded va Flexible - bo'sh joyni moslashuvchan egallash uchun. LayoutBuilder hamda MediaQuery yordamida turli o'lchamli ekranlarga mos UI yaratiladi.",
                    codeSnippet = "Row(\n  mainAxisAlignment: MainAxisAlignment.spaceBetween,\n  children: [\n    const Icon(Icons.star),\n    Expanded(child: Container(color: Colors.blue, height: 50)),\n    const Icon(Icons.check),\n  ],\n)"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q9_1",
                    question = "Row yoki Column ichidagi elementga qolgan barcha bo'sh joyni egallash imkonini beruvchi widget qaysi?",
                    options = listOf("Container", "SizedBox", "Expanded", "Card"),
                    correctIndex = 2,
                    explanation = "Expanded vidjeti Row yoki Column o'qi bo'yicha qolgan barcha bo'sh joyni to'liq qamrab oladi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c9",
                title = "Row va Expanded",
                instruction = "Row ichida Expanded va Icon joylashtiring:",
                startingCode = "Widget build(BuildContext context) {\n  return Row(\n    children: [\n      Icon(Icons.star),\n      Expanded(child: Text('Bo\\'sh joy')),\n    ],\n  );\n}",
                solutionCode = "Widget build(BuildContext context) {\n  return Row(\n    children: [\n      Icon(Icons.star),\n      Expanded(child: Text('Bo\\'sh joy')),\n    ],\n  );\n}",
                requiredKeywords = listOf("Row", "children", "Expanded"),
                widgetType = SimulatedWidgetType.ROW_ICONS,
                hint = "Row(children: [Icon(Icons.star), Expanded(...)])",
                initialPreviewParam = "Row"
            )
        ),

        // LEVEL 10
        Lesson(
            id = "level_10",
            moduleNumber = 3,
            moduleTitle = "3-Bosqich: Flutter UI & Vidjetlar Olami",
            orderNumber = 10,
            title = "Level 10: Ro'yxatlar va Murakkab UI Elementlari",
            subtitle = "ListView.builder, GridView, TextField va Dialoglar",
            iconName = "format_list_numbered",
            isFree = false,
            unlockCost = 70,
            xpReward = 550,
            coinReward = 65,
            estimatedMinutes = 8,
            theorySections = listOf(
                TheorySection(
                    title = "Dinamik Ro'yxatlar (ListView.builder)",
                    content = "Ko'p miqdordagi ma'lumotlarni ko'rsatish uchun ListView.builder va GridView.builder ishlatiladi. Ular faqat ekranda ko'rinib turgan elementlarni xotirada yaratadi. Stack va Positioned - elementlarni ustma-ust joylashtirish uchun. TextField va Form / TextFormField - foydalanuvchi ma'lumot kiritishi va uni tekshirish uchun. Dialoglar: showDialog, AlertDialog, showModalBottomSheet.",
                    codeSnippet = "ListView.builder(\n  itemCount: 100,\n  itemBuilder: (context, index) {\n    return ListTile(\n      title: Text('Element #\$index'),\n    );\n  },\n)"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q10_1",
                    question = "Minglab elementlardan iborat ro'yxatni xotirani tejagan holda tezkor chiqarish uchun qaysi widget ishlatiladi?",
                    options = listOf("Column", "SingleChildScrollView", "ListView.builder", "Row"),
                    correctIndex = 2,
                    explanation = "ListView.builder faqat ekranda ko'rinib turgan elementlarni 'lazy load' usulida chizadi va xotirani tejaydi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c10",
                title = "ListView.builder yaratish",
                instruction = "itemCount: 10 va ListTile qaytaruvchi ListView.builder yozing:",
                startingCode = "Widget build(BuildContext context) {\n  return ListView.builder(\n    itemCount: 10,\n    itemBuilder: (context, index) => ListTile(title: Text('Element \$index')),\n  );\n}",
                solutionCode = "Widget build(BuildContext context) {\n  return ListView.builder(\n    itemCount: 10,\n    itemBuilder: (context, index) => ListTile(title: Text('Element \$index')),\n  );\n}",
                requiredKeywords = listOf("ListView.builder", "itemCount", "ListTile"),
                widgetType = SimulatedWidgetType.LIST_VIEW,
                hint = "ListView.builder(itemCount: 10, ...)",
                initialPreviewParam = "ListView"
            )
        ),

        // LEVEL 11
        Lesson(
            id = "level_11",
            moduleNumber = 4,
            moduleTitle = "4-Bosqich: Holat Boshqaruvi va Ma'lumotlar",
            orderNumber = 11,
            title = "Level 11: Navigatsiya va Marshrutlash (Routing)",
            subtitle = "Navigator.push(), pop() va Named Routes",
            iconName = "route",
            isFree = false,
            unlockCost = 75,
            xpReward = 600,
            coinReward = 70,
            estimatedMinutes = 8,
            theorySections = listOf(
                TheorySection(
                    title = "Navigator va Sahifalar Staki",
                    content = "Flutter'da sahifalar stack (g'amla) ko'rinishida boshqariladi. Navigator.push() - yangi sahifani ustiga qo'shadi, Navigator.pop() - joriy sahifani yopadi. Nomlangan marshrutlar (Named Routes): MaterialApp ichida routes xaritasini belgilab, Navigator.pushNamed() orqali sahifalarga o'tiladi. Sahifalar o'rtasida argumentlar va natijaviy ma'lumotlar uzatiladi.",
                    codeSnippet = "// Yangi sahifaga o'tish\nNavigator.push(\n  context,\n  MaterialPageRoute(builder: (context) => const DetailScreen()),\n);\n// Ortga qaytish\nNavigator.pop(context);"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q11_1",
                    question = "Joriy sahifani yopib, oldingi sahifaga qaytish uchun qaysi buyruq chaqiriladi?",
                    options = listOf("Navigator.push()", "Navigator.pop()", "Navigator.close()", "Navigator.back()"),
                    correctIndex = 1,
                    explanation = "Navigator.pop(context) joriy faol ekranni yopadi va navigatsiya stakidan olib tashlaydi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c11",
                title = "Orqaga qaytish (pop)",
                instruction = "onPressed ichida Navigator.pop(context) buyrug'ini chaqiring:",
                startingCode = "ElevatedButton(\n  onPressed: () {\n    Navigator.pop(context);\n  },\n  child: Text('Ortga'),\n)",
                solutionCode = "ElevatedButton(\n  onPressed: () {\n    Navigator.pop(context);\n  },\n  child: Text('Ortga'),\n)",
                requiredKeywords = listOf("Navigator.pop", "context"),
                widgetType = SimulatedWidgetType.APP_BAR_SCAFFOLD,
                hint = "Navigator.pop(context);",
                initialPreviewParam = "Ortga"
            )
        ),

        // LEVEL 12
        Lesson(
            id = "level_12",
            moduleNumber = 4,
            moduleTitle = "4-Bosqich: Holat Boshqaruvi va Ma'lumotlar",
            orderNumber = 12,
            title = "Level 12: State Management (Holat Boshqaruvi)",
            subtitle = "setState(), Provider, BLoC/Cubit va Riverpod",
            iconName = "sync",
            isFree = false,
            unlockCost = 80,
            xpReward = 650,
            coinReward = 75,
            estimatedMinutes = 9,
            theorySections = listOf(
                TheorySection(
                    title = "Global State Management",
                    content = "Katta ilovalarda ma'lumotlar o'zgarishini butun ilova bo'ylab uzatish uchun State Management kerak. setState() faqat lokal widget uchun mos. Provider — ChangeNotifier va Consumer yordamida reaktiv ilovalar qurish imkonini beradi. BLoC / Cubit — biznes mantiqni (Business Logic Component) UI qismidan to'liq ajratadi va voqealar (Events) hamda holat (States) bilan ishlaydi.",
                    codeSnippet = "class CounterProvider extends ChangeNotifier {\n  int _count = 0;\n  int get count => _count;\n  void increment() {\n    _count++;\n    notifyListeners();\n  }\n}"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q12_1",
                    question = "Provider paketida tinglovchilarga ma'lumot o'zgargani haqida xabar berish uchun qaysi metod chaqiriladi?",
                    options = listOf("setState()", "notifyListeners()", "update()", "refresh()"),
                    correctIndex = 1,
                    explanation = "ChangeNotifier sinfidagi notifyListeners() barcha tinglovchi vidjetlarga UI ni yangilash buyrug'ini beradi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c12",
                title = "notifyListeners chaqiruvi",
                instruction = "CounterProvider ichida notifyListeners() metodini chaqiring:",
                startingCode = "void increment() {\n  _count++;\n  notifyListeners();\n}",
                solutionCode = "void increment() {\n  _count++;\n  notifyListeners();\n}",
                requiredKeywords = listOf("notifyListeners"),
                widgetType = SimulatedWidgetType.COUNTER_APP,
                hint = "notifyListeners();",
                initialPreviewParam = "1"
            )
        ),

        // LEVEL 13
        Lesson(
            id = "level_13",
            moduleNumber = 4,
            moduleTitle = "4-Bosqich: Holat Boshqaruvi va Ma'lumotlar",
            orderNumber = 13,
            title = "Level 13: Local Database (Lokal Baza) va Fayllar",
            subtitle = "SharedPreferences, SQFlite va path_provider",
            iconName = "storage",
            isFree = false,
            unlockCost = 85,
            xpReward = 700,
            coinReward = 80,
            estimatedMinutes = 9,
            theorySections = listOf(
                TheorySection(
                    title = "Lokal Xotira va Ma'lumotlar Bazasi",
                    content = "Lokal ma'lumotlarni saqlash offline ishlash imkonini beradi. SharedPreferences - kichik ma'lumotlarni (sozlamalar, token, til) kalit-qiymat shaklida saqlaydi. SQFlite - mobil qurilma ichida to'liq relatsion SQLite ma'lumotlar bazasini yaratadi (CRUD: Create, Read, Update, Delete). path_provider - fayllarni qurilma xotirasiga saqlash yo'llarini aniqlaydi.",
                    codeSnippet = "// SharedPreferences misoli\nfinal prefs = await SharedPreferences.getInstance();\nawait prefs.setString('user_token', 'xyz123');\nString? token = prefs.getString('user_token');"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q13_1",
                    question = "Ilova sozlamalari yoki foydalanuvchi tokenini kalit-qiymat shaklida sodda saqlash uchun qaysi kutubxona ishlatiladi?",
                    options = listOf("SQFlite", "SharedPreferences", "HTTP", "Provider"),
                    correctIndex = 1,
                    explanation = "SharedPreferences kichik sozlamalar va sessiya ma'lumotlarini tezkor kalit-qiymat tarzida saqlash uchun qulay."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c13",
                title = "SharedPreferences bilan saqlash",
                instruction = "prefs.setString('kalit', 'qiymat') orqali ma'lumot saqlang:",
                startingCode = "final prefs = await SharedPreferences.getInstance();\nawait prefs.setString('username', 'FlutterDev');",
                solutionCode = "final prefs = await SharedPreferences.getInstance();\nawait prefs.setString('username', 'FlutterDev');",
                requiredKeywords = listOf("SharedPreferences", "setString"),
                widgetType = SimulatedWidgetType.CONTAINER_TEXT,
                hint = "await prefs.setString('username', 'FlutterDev');",
                initialPreviewParam = "Saved"
            )
        ),

        // LEVEL 14
        Lesson(
            id = "level_14",
            moduleNumber = 5,
            moduleTitle = "5-Bosqich: REST API, Firebase va Reliz",
            orderNumber = 14,
            title = "Level 14: REST API, JSON va Networking",
            subtitle = "HTTP so'rovlar, jsonDecode va FutureBuilder",
            iconName = "cloud_sync",
            isFree = false,
            unlockCost = 90,
            xpReward = 750,
            coinReward = 85,
            estimatedMinutes = 10,
            theorySections = listOf(
                TheorySection(
                    title = "Tarmoq bilan Ishlash (HTTP & REST API)",
                    content = "Server bilan ma'lumot almashish uchun HTTP protokoli ishlatiladi (http yoki dio paketlari). GET (ma'lumot olish), POST (yangi ma'lumot qo'shish), PUT/PATCH (yangilash), DELETE (o'chirish). Serverdan kelgan JSON matni Dart obyektlariga o'giriladi (fromJson / toJson model konstruktorlari). FutureBuilder widgeti asinxron so'rov natijasini (yuklanish, muvaffaqiyat, xato) UI da chiqarib beradi.",
                    codeSnippet = "import 'package:http/http.dart' as http;\nimport 'dart:convert';\n\nFuture<void> fetchPosts() async {\n  final response = await http.get(Uri.parse('https://api.example.com/posts'));\n  if (response.statusCode == 200) {\n    List data = jsonDecode(response.body);\n    print('Postlar soni: \${data.length}');\n  }\n}"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q14_1",
                    question = "Serverdan kelgan JSON matnini Dart obyektlariga aylantirish uchun qaysi funksiya ishlatiladi?",
                    options = listOf("jsonEncode()", "jsonDecode()", "parseString()", "toDart()"),
                    correctIndex = 1,
                    explanation = "jsonDecode() satrli JSON formatini Dart tushunadigan List yoki Map obyektiga aylantirib beradi."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c14",
                title = "jsonDecode ishlatish",
                instruction = "jsonDecode orqali JSON matnini o'qib oling:",
                startingCode = "import 'dart:convert';\nvoid parse() {\n  var data = jsonDecode('{\"name\": \"Flutter\"}');\n  print(data['name']);\n}",
                solutionCode = "import 'dart:convert';\nvoid parse() {\n  var data = jsonDecode('{\"name\": \"Flutter\"}');\n  print(data['name']);\n}",
                requiredKeywords = listOf("jsonDecode", "data"),
                widgetType = SimulatedWidgetType.CONTAINER_TEXT,
                hint = "var data = jsonDecode(...);",
                initialPreviewParam = "Flutter"
            )
        ),

        // LEVEL 15
        Lesson(
            id = "level_15",
            moduleNumber = 5,
            moduleTitle = "5-Bosqich: REST API, Firebase va Reliz",
            orderNumber = 15,
            title = "Level 15: Firebase, Animatsiyalar va Reliz (Play Store)",
            subtitle = "Firebase Auth/Firestore, AAB va relizga chiqarish",
            iconName = "military_tech",
            isFree = false,
            unlockCost = 100,
            xpReward = 800,
            coinReward = 100,
            estimatedMinutes = 10,
            theorySections = listOf(
                TheorySection(
                    title = "Firebase va Ilovani Do'konga Joylash",
                    content = "Firebase — Google'ning BaaS platformasi bo'lib, Firebase Auth (autentifikatsiya), Firestore (realtime baza) va Cloud Messaging (Push xabarnomalar) xizmatlarini beradi. Animatsiyalar (Hero, AnimatedContainer) ilova interfeysini juda jozibador qiladi. Reliz bosqichi: App Icon (flutter_launcher_icons) va Splash Screen o'rnatish. Android uchun Android App Bundle (.aab) fayli yaratilib, Google Play Console'ga yuklanadi. iOS uchun esa Xcode orqali App Store'ga nashr etiladi.",
                    codeSnippet = "// Android Release App Bundle yaratish buyrug'i:\n// flutter build appbundle --release\nHero(\n  tag: 'profile_pic',\n  child: Image.asset('assets/user.png'),\n)"
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = "q15_1",
                    question = "Google Play Store'ga yuklash uchun tavsiya etiladigan zamonaviy Android reliz formati qaysi?",
                    options = listOf(".apk", ".aab (App Bundle)", ".exe", ".ipa"),
                    correctIndex = 1,
                    explanation = "Google Play 2021-yildan boshlab rasmiy ravishda barcha yangi ilovalar uchun Android App Bundle (.aab) formatini majburiy qilib belgilagan."
                )
            ),
            codingChallenge = CodingChallenge(
                id = "c15",
                title = "App Bundle buyrug'i",
                instruction = "Reliz uchun appbundle buyrug'ini kiriting:",
                startingCode = "// Terminalda buyruq:\n// flutter build appbundle --release",
                solutionCode = "// Terminalda buyruq:\n// flutter build appbundle --release",
                requiredKeywords = listOf("flutter build appbundle"),
                widgetType = SimulatedWidgetType.CONTAINER_TEXT,
                hint = "flutter build appbundle --release",
                initialPreviewParam = "Release AAB"
            )
        )
    )

    fun getUserProfile(): UserProfile {
        val completedLessons = getStringSet(KEY_COMPLETED_LESSONS, emptySet())
        val unlockedLessons = getStringSet(
            KEY_UNLOCKED_LESSONS,
            setOf("level_1", "level_2", "level_3")
        )
        val claimedAchievements = getStringSet(KEY_CLAIMED_ACHIEVEMENTS, emptySet())
        val completedChallenges = getStringSet(KEY_COMPLETED_CHALLENGES, emptySet())

        val xp = prefs.getInt(KEY_XP, 120)
        val level = (xp / 150) + 1

        val darkModeStr = prefs.getString(KEY_DARK_MODE, null)
        val isDarkMode = when (darkModeStr) {
            "true" -> true
            "false" -> false
            else -> null
        }

        return UserProfile(
            username = prefs.getString(KEY_USERNAME, "Flutter_Master") ?: "Flutter_Master",
            avatarEmoji = prefs.getString(KEY_AVATAR, "🚀") ?: "🚀",
            xp = xp,
            level = level,
            coins = prefs.getInt(KEY_COINS, 100),
            hearts = prefs.getInt(KEY_HEARTS, 5),
            streakDays = prefs.getInt(KEY_STREAK, 3),
            lastDailyClaimDate = prefs.getLong(KEY_LAST_DAILY, 0L),
            isProSubscriber = prefs.getBoolean(KEY_IS_PRO, false),
            completedLessonIds = completedLessons,
            unlockedLessonIds = unlockedLessons,
            downloadedLessonIds = emptySet(),
            claimedAchievementIds = claimedAchievements,
            completedChallengeIds = completedChallenges,
            correctQuizzesCount = prefs.getInt(KEY_QUIZ_CORRECT, 0),
            totalQuizzesTaken = prefs.getInt(KEY_QUIZ_TOTAL, 0),
            isDarkMode = isDarkMode
        )
    }

    fun updateProfile(profile: UserProfile) {
        prefs.edit().apply {
            putString(KEY_USERNAME, profile.username)
            putString(KEY_AVATAR, profile.avatarEmoji)
            putInt(KEY_XP, profile.xp)
            putInt(KEY_LEVEL, profile.level)
            putInt(KEY_COINS, profile.coins)
            putInt(KEY_HEARTS, profile.hearts)
            putInt(KEY_STREAK, profile.streakDays)
            putLong(KEY_LAST_DAILY, profile.lastDailyClaimDate)
            putBoolean(KEY_IS_PRO, profile.isProSubscriber)
            putStringSet(KEY_COMPLETED_LESSONS, profile.completedLessonIds)
            putStringSet(KEY_UNLOCKED_LESSONS, profile.unlockedLessonIds)
            putStringSet(KEY_CLAIMED_ACHIEVEMENTS, profile.claimedAchievementIds)
            putStringSet(KEY_COMPLETED_CHALLENGES, profile.completedChallengeIds)
            putInt(KEY_QUIZ_CORRECT, profile.correctQuizzesCount)
            putInt(KEY_QUIZ_TOTAL, profile.totalQuizzesTaken)
            when (profile.isDarkMode) {
                true -> putString(KEY_DARK_MODE, "true")
                false -> putString(KEY_DARK_MODE, "false")
                null -> remove(KEY_DARK_MODE)
            }
            apply()
        }
    }

    fun getAchievements(profile: UserProfile): List<Achievement> {
        val completedLessonsCount = profile.completedLessonIds.size
        val completedChallengesCount = profile.completedChallengeIds.size

        return listOf(
            Achievement(
                id = "first_step",
                title = "Birinchi Qadam",
                description = "Ilk Flutter darajasini muvaffaqiyatli yakunlang",
                emoji = "🌱",
                coinReward = 30,
                xpReward = 50,
                currentProgress = completedLessonsCount.coerceAtMost(1),
                maxProgress = 1,
                isCompleted = completedLessonsCount >= 1,
                isClaimed = profile.claimedAchievementIds.contains("first_step")
            ),
            Achievement(
                id = "code_warrior",
                title = "Kod Ustasi",
                description = "Kamida 5 ta kodlash topshirig'ini to'g'ri bajaring",
                emoji = "💻",
                coinReward = 60,
                xpReward = 100,
                currentProgress = completedChallengesCount.coerceAtMost(5),
                maxProgress = 5,
                isCompleted = completedChallengesCount >= 5,
                isClaimed = profile.claimedAchievementIds.contains("code_warrior")
            ),
            Achievement(
                id = "quiz_master",
                title = "Viktorina Chempioni",
                description = "Testlarda 10 ta savolga to'g'ri javob bering",
                emoji = "🧠",
                coinReward = 50,
                xpReward = 80,
                currentProgress = profile.correctQuizzesCount.coerceAtMost(10),
                maxProgress = 10,
                isCompleted = profile.correctQuizzesCount >= 10,
                isClaimed = profile.claimedAchievementIds.contains("quiz_master")
            ),
            Achievement(
                id = "streak_fire",
                title = "Olovli Seriya",
                description = "3 kun davomida ilovada uzluksiz o'rganing",
                emoji = "🔥",
                coinReward = 60,
                xpReward = 80,
                currentProgress = profile.streakDays.coerceAtMost(3),
                maxProgress = 3,
                isCompleted = profile.streakDays >= 3,
                isClaimed = profile.claimedAchievementIds.contains("streak_fire")
            ),
            Achievement(
                id = "flutter_pro",
                title = "Flutter Magistri",
                description = "Kamida 10 ta darajani to'liq zabt eting",
                emoji = "👑",
                coinReward = 150,
                xpReward = 300,
                currentProgress = completedLessonsCount.coerceAtMost(10),
                maxProgress = 10,
                isCompleted = completedLessonsCount >= 10,
                isClaimed = profile.claimedAchievementIds.contains("flutter_pro")
            )
        )
    }

    fun getLeaderboard(currentUserProfile: UserProfile): List<LeaderboardEntry> {
        val staticPeers = listOf(
            LeaderboardEntry("u1", 1, "Sardor_FlutterDev", "🦅", 1250, "Olmos", 15),
            LeaderboardEntry("u2", 2, "Malika_DartQueen", "👑", 980, "Olmos", 13),
            LeaderboardEntry("u3", 3, "Jasur_CodeKnight", "⚡", 840, "Olmos", 11),
            LeaderboardEntry("u4", 4, "Bekzod_WidgetMaster", "🔥", 680, "Oltin", 9),
            LeaderboardEntry("u5", 5, "Shahlo_TechUz", "🌸", 550, "Oltin", 8),
            LeaderboardEntry("u6", 6, "Azizbek_Mobile", "🚀", 430, "Oltin", 6),
            LeaderboardEntry("u7", 7, "Nodira_Programmer", "💻", 310, "Kumush", 5),
            LeaderboardEntry("u8", 8, "Farrux_Coder", "🎯", 240, "Kumush", 4),
            LeaderboardEntry("u9", 9, "Kamola_Dev", "✨", 180, "Bronza", 3),
            LeaderboardEntry("u10", 10, "Otabek_Junior", "🌱", 90, "Bronza", 2)
        )

        val currentEntry = LeaderboardEntry(
            id = "current_user",
            rank = 0,
            username = currentUserProfile.username,
            avatarEmoji = currentUserProfile.avatarEmoji,
            xp = currentUserProfile.xp,
            league = when {
                currentUserProfile.xp >= 700 -> "Olmos"
                currentUserProfile.xp >= 400 -> "Oltin"
                currentUserProfile.xp >= 200 -> "Kumush"
                else -> "Bronza"
            },
            completedLessons = currentUserProfile.completedLessonIds.size,
            isCurrentUser = true
        )

        val all = (staticPeers + currentEntry).sortedByDescending { it.xp }
        return all.mapIndexed { index, item ->
            item.copy(rank = index + 1)
        }
    }

    private fun getStringSet(key: String, def: Set<String>): Set<String> {
        return prefs.getStringSet(key, def) ?: def
    }
}
