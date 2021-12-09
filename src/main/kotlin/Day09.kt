import Day09.HeightMap
import Day09.input

fun main() {

    val heightMap = HeightMap.from(input)
    val lowPoints = heightMap.lowPoints()

    val answer1 = lowPoints.sumOf { 1 + it.height }
    println(answer1) // 444

    val basinSizes = lowPoints.map { heightMap.basinSize(it) }
    val answer2 = basinSizes.sorted().takeLast(3).reduce { a, b -> a * b }
    println(answer2) // 1168440
}

object Day09 {

    data class Coord(val x: Int, val y: Int)
    data class Point(val coord: Coord, val height: Int)

    data class HeightMap(val pointMap: Map<Coord, Point>, val xMax: Int, val yMax: Int) {

        private fun adjacents(c: Coord): Set<Point> = setOfNotNull(
            if (c.x != 0)    pointMap[c.copy(x = c.x - 1)] else null,
            if (c.x != xMax) pointMap[c.copy(x = c.x + 1)] else null,
            if (c.y != 0)    pointMap[c.copy(y = c.y - 1)] else null,
            if (c.y != yMax) pointMap[c.copy(y = c.y + 1)] else null,
        )

        fun lowPoints(): List<Point> =
            (0..yMax).flatMap { y ->
                (0..xMax).mapNotNull { x ->
                    val point = pointMap.getValue(Coord(x, y))
                    if (adjacents(point.coord).all { point.height < it.height }) point else null
                }
            }

        fun basinSize(lowPoint: Point): Int {

            tailrec fun go(acc: Set<Point>): Set<Point> {
                val next = acc.flatMap { p -> adjacents(p.coord).filter { adj -> adj.height != 9 } + p }.toSet()
                return if (next == acc) acc else go(next)
            }

            return go(setOf(lowPoint).toSet()).size
        }

        companion object {
            fun from(s: String): HeightMap {
                val lines = s.lines()
                return HeightMap(
                    pointMap = lines.flatMapIndexed { y, line ->
                        line.mapIndexed { x, char ->
                            Coord(x, y).let { c -> c to Point(c, char.digitToInt()) }
                        }
                    }.toMap(),
                    xMax = lines.first().length - 1,
                    yMax = lines.size -1
                )
            }
        }
    }

    val input = """
        7678934989765431234965432345679567899987999876543210198965019878921987654343498765678932445678998678
        4567929878965420129874301286789456789976898989654321987894329767990986543232349874589321234567987567
        5679896567895431298763212878992345999875787898769439876789498656789875432101234965695410145678998456
        6798765457889942987654324567891239898764656999877698765678987645898976543432349876789521234567899679
        7987674345679899999765465678932398765832345899998959854689965434567897676546567989895432347878965989
        8999543256798777898976576789993987654521456789999843212569896545678998787959698991976543956799654695
        9987652127987665767897689899989876543210345679898754343456798656789239899868789432398659897989543234
        5498763439876543458789789999876999854321234599649877654767899769893123978979896543698798789878954345
        4329874598654432344678994599995498765435345987430998767878943978932012367989987654569987698767895467
        3212985798743210123489123989985259876746459876321249988989652989893134456799998765879896548656789578
        4339987987654321234691019979874345987897898765432356799199871296789245667898999976998785438745678989
        5498798998878534575692198767965456798998939878543467899299852345678956789987899897987654321236789799
        6987659109986545689789987659876567899659321989654568978989643456789767899876788789998776434545697678
        9876543219999876799899876546989878987543210198765679767678954587899898956985897678949996547656789799
        1987654998943989899987985434599989598657432349876789656569765678987939349854864589234987858767899989
        0198789887894599989996543515678993498796545456987898743478976789876321298763453678949898969898989878
        1999898776795999878989432101789932349987656567898989832567897899965490989541012567898769878999878967
        9899987685679889769878943232597891234598767899999876543478998999876989876432123456789656989298765456
        8789876554798778654567894743456789345679878923498987654569129989989976987543234678996545392129532367
        7698765443987664543456797654567899956989989014987698765678998877998765498954545689987632129098921278
        6569954212396543212345898765678999899898799195696549879899987666897654349765656789876543098997892389
        3459864343498752101456789878789198785787678989987832989999876545698743239878767897987654987886789492
        2398765674599543412467899999891097673654569878999421999998765436789632134999898976898789876795678991
        3499876965987654325698978988932986542323698767698939878999876521296541013456999285999898765644567889
        4989987899998765434789769876549876541012398654567998767899987990965432123467892134598999874323456978
        9878998998979879545678954987656985432143987843456789656989999889897545678578943245987898765401234567
        8767899877569998776989012999767896543459876532345678945778989678789656799989987656796789876512345678
        7658998766458799887899929898978997957867989665456789234569876567678998892399998767985478984323456989
        8745989854345678998999898787899989898978998776569894129678985425467889910978999899876367965434567891
        1239877541234589659298797656789876789989329987678953298989893212345678951867899987985459876565678932
        0198765430147678940129654345896545679096598798789874987899794101234989743456789876798567987676989543
        1239954321256789432334963234989632889129987679899989876997693212567897654867899965987678998789899994
        4345965432367898743949892145678921299298995423998798765434589323456789965979989874398999109898798789
        5459876563459999659898789236989545678987893212989659878321678934567897896998879983219999912987687678
        6567987676567898998787678947899856789456954909876543987430189765678956799897569994301989899876543587
        7678998987698987997654567898998767899397899899965452396542239876789545698786478989319876798767432456
        8989769998789996789879678929349878998989998789876321987963458989895432987645345678999985679654321347
        9995456999899985698998789210198989987678987678988432399874567894976321296532123456789876798768436458
        8764367898999874587899894321987899896567896569876543498985678923965432397832012567899987899876547969
        8743234567898963256789995939876798765456789456989654987696789019876943498945123678999898976998679898
        7632123458987654145678989898765987654345894345698769876549894125988767569656734589998789985498798767
        6543014567898543236789876789874398765656953234569898765634943234599878998767845678945678976569989656
        9667323467899876347898785459995219876769652125678987654323794545679989999898976789234567897978976547
        8778434568959875467897654398989323989878943012567898765474689678998997898979797890195998998989865434
        9989545678943976778998769987678934699989542123456999878565678999996545987667698991989899239299754323
        3597676789432987989999898996567895678997674334678987999678789989889239876555459789976789393198643514
        2498787999921099998989987887478986789498765445789876569989898876778998765434345678965678989019432101
        3569898939892123987679876782347897896329876786898763458999977745769899874321234567894389678998565212
        9679979328789239876598765431256989965212987897987654567899865632356798765410123478943234567987654323
        8998765412678998965439877632345678954323998998998765678998754321467999876323234569432123456798765634
        7899976543489987654323998543458989895439879769999876789789854210349898987434546678943234897899878795
        6789897654567898765214569654567898789598765657889987997698765321298787898865678989874346789954989896
        5976789765789989876501239865678975678987654345678998954579877452987676789976789599865457898543292987
        4345899876789876975423345976789214567898943234989879563459976569876555678987892459876568987652101298
        3234989989898765987564569987894323878989432129898765432398987699765434599099921012987679876543212349
        0125678999987654598965878998989439989876543298789876321297898987654321989198999129898789987654324556
        1237889789876543459876989439878998996989656989678943210986789998765439878987988998789999998875434567
        4356897654987659767987898998769877895498769878569656921975679989896798767896567897678998989998565678
        5456789543299998978998967987654566789329899767498969899864589878987899854578456789567987776597676989
        6589897632198767899219459876543045678914987654347898789653298767898998763562349895459876567459789991
        7678998943987656998901367965432123489323999543216987678943109456789999872101238954398765482359898910
        9889569959998549897899459987643234595439898932105698567893298967897895983432867893239654321454967891
        6993459898987698786788998798654345696598787943214595466789987898976794594543456789398763210123459942
        5432398767898987674597897659765456789987656894323986345679876799765789965654567898999876321254568953
        6543987656999876563456789749876567899878547895434595456789965689654569876767878967899965432345778964
        7679876545789995432367997439987698978965436789545698567899876789323456989898989456989996763456789765
        8798987434599989321459896598998999765432125678956987689901987895434568992949995345678987654567899896
        9987894323989878932998789987679899875432014589767898789912398998567899101239854234567898765678954987
        9876789439878769899877678987598798989543123698978999897893459987678973212398765123498999876789543098
        8765678998767456798765567896454687899955954567899896946989567898989765434569876764689998987899932129
        8754567987654323998643456789323456789876865678998785435679698929999876545678989985699987498999899945
        7643489998543219865432347893212345699997977899987654323798989019876987876899599876789876349998768896
        8732578998654301976545456789103457789999998999876543212987678998765498997945456987898765234987656789
        6543678959765214989856567893234569996987899298765432109876578999896399989432345698919954139876545899
        7654789349874323498767678954645678945876789349876543499765467890987989878991234569109893239985434578
        8765891299985434569878989765676789434765679459987654987654356891999876765789945678998789398765523456
        9976910989876545678989999877987896523254768998998765698775456789899765774677896789987658987654312348
        4987899976987678989998999998998965410123459987989979789876589899768954523456987893299767898732101267
        3298998765698989199987898999999654321234567896567989895987678998656893212347898932109878999843212456
        2129219854349993239876567899989876432389678985456999954398789987434789101256789543299989987656323567
        3998998765457899398985457789876987545678999876567899843219899986525678919397899956989999998767439678
        9897899879568998987654345678965987656789998987678998765401998897636789998989999899878999879896568789
        7776901998979987698788234567894598789899897698989987654323456789647899987679998798767898765987679892
        6545892987899876549887545678943219898999789549099999875634578998757999898567899632347999894398989943
        5434799876989765432998656789894345967988678932129876988785699109898998765456789543456789989219397994
        4327689945678954321298797895789959659876569893998965499896789212999899984345697654567899876101236789
        5212578934567895532789989934567898945989989789897894345987894323498769875456789765688999865412345699
        4323459323656789645679878929689967896794398698756789234598995459987654986568999878799987654323456789
        6496589212345678956898767898998756789899987598767892123999989698976543987779872989892198865434567896
        7587678923456789877989856787897645678998765459899921019899878997895432398889761098963239978545699975
        8698989634587893989876543836989534567899876567995432198789769876789521239997653197654347987656989654
        8789998545678932395978532125678923456789989979989543987698954545678994345679794989987456799769978932
        9899987656789541234965432034589104567899899899878959876567893234568989456798989678976567899898867941
        9998798967895410199876543456678915678998789798765899999456789125679878967987878567897678910997658910
        9897659878964321987998954568789896789679644699876789878349893236898767899876563456789989899879846891
        8799543989965439876339769878896799894598533989989898965258954345987656988986432345999998798765735569
        7678942397897598765219898989945689923987212378899987654147895456988743877898321234568987659874323478
        6569891456789679854329987893234578939876101256789998943236789679876432356789432345678986545985412567
        3456789967898789965498966534134689545965432345678929854345678989765421245679544856789976439876323478
        4567999878999999876987654321012789659878543656789219765656789999876532476789667767899876545987454567
    """.trimIndent()
}