import Day05.Line
import Day05.countOverlaps
import Day05.input
import kotlin.math.abs

fun main() {

    val lines = input.map { it.split(" -> ", ",") }.map { Line.from(it) }

    val answer1 = countOverlaps(lines) { it.axialCoords() ?: emptyList() }
    println(answer1) // 5442

    val answer2 = countOverlaps(lines) { it.axialCoords() ?: it.diagCoords() ?: emptyList() }
    println(answer2) // 19571
}

object Day05 {

    data class Coord(val x: Int, val y: Int)

    data class Line(val p1: Coord, val p2: Coord) {

        fun axialCoords(): List<Coord>? = when {
            p1.x == p2.x -> range(p1.y, p2.y).map { y -> Coord(p1.x, y) }
            p1.y == p2.y -> range(p1.x, p2.x).map { x -> Coord(x, p1.y) }
            else -> null
        }

        fun diagCoords(): List<Coord>? =
            if (abs(p1.x - p2.x) == abs(p1.y - p2.y))
                range(p1.x, p2.x).zip(range(p1.y, p2.y)).map { (x, y) -> Coord(x, y) }
            else null

        private fun range(a: Int, b: Int): List<Int> = if (a < b) (a..b).toList() else a.downTo(b).toList()

        companion object {
            fun from(values: List<String>) = values.map { it.toInt() }.let { (x1, y1, x2, y2) ->
                Line(Coord(x1, y1), Coord(x2, y2))
            }
        }
    }

    fun countOverlaps(lines: List<Line>, fn: (Line) -> List<Coord>): Int =
        lines.flatMap { fn(it) }.groupBy { it }.count { (_, v) -> v.size > 1 }

    val input: List<String> = """
        62,963 -> 844,181
        58,85 -> 917,944
        137,76 -> 137,347
        453,125 -> 347,19
        178,65 -> 977,864
        447,360 -> 62,745
        723,326 -> 156,893
        47,497 -> 107,437
        387,491 -> 340,491
        58,477 -> 283,252
        86,351 -> 562,827
        215,172 -> 539,172
        496,801 -> 496,63
        546,412 -> 232,98
        621,807 -> 481,807
        471,20 -> 618,20
        175,283 -> 175,467
        19,283 -> 19,290
        159,137 -> 159,11
        593,181 -> 543,181
        167,976 -> 929,976
        730,782 -> 959,782
        713,285 -> 713,880
        583,144 -> 583,296
        39,61 -> 961,983
        778,81 -> 604,81
        70,560 -> 70,889
        85,129 -> 666,710
        689,688 -> 632,688
        76,52 -> 903,879
        510,543 -> 22,55
        510,935 -> 470,935
        780,357 -> 780,602
        440,349 -> 710,79
        934,801 -> 412,801
        979,25 -> 35,969
        379,527 -> 379,76
        243,524 -> 243,664
        534,945 -> 11,422
        198,367 -> 224,367
        871,451 -> 456,451
        226,231 -> 939,231
        686,354 -> 740,300
        543,68 -> 340,68
        506,160 -> 319,347
        177,25 -> 177,603
        337,450 -> 724,450
        421,519 -> 676,519
        858,976 -> 179,297
        236,222 -> 236,250
        254,242 -> 254,626
        859,243 -> 23,243
        89,982 -> 979,92
        58,758 -> 101,801
        930,483 -> 587,826
        667,717 -> 667,762
        512,816 -> 845,816
        17,501 -> 17,760
        345,61 -> 847,61
        531,840 -> 618,840
        67,748 -> 262,748
        548,461 -> 163,846
        934,142 -> 169,907
        119,931 -> 580,470
        769,916 -> 457,604
        587,458 -> 93,458
        109,850 -> 768,191
        225,129 -> 160,64
        544,163 -> 544,476
        304,594 -> 61,351
        510,396 -> 510,741
        772,210 -> 772,889
        867,415 -> 721,269
        466,266 -> 466,44
        305,609 -> 305,237
        563,962 -> 451,962
        566,402 -> 28,940
        889,717 -> 891,717
        754,545 -> 313,545
        930,976 -> 209,255
        70,911 -> 692,289
        737,37 -> 958,37
        652,566 -> 720,634
        776,551 -> 370,957
        484,476 -> 820,476
        119,420 -> 639,420
        394,964 -> 394,221
        340,767 -> 964,143
        715,289 -> 481,55
        236,389 -> 826,389
        747,642 -> 33,642
        583,351 -> 244,690
        609,17 -> 609,680
        460,365 -> 668,365
        519,180 -> 929,590
        206,45 -> 782,45
        507,185 -> 386,306
        16,12 -> 982,978
        31,348 -> 320,348
        54,975 -> 947,82
        844,714 -> 870,714
        677,965 -> 677,699
        387,699 -> 387,26
        329,479 -> 189,479
        970,708 -> 538,708
        565,434 -> 565,623
        748,737 -> 748,497
        255,984 -> 255,600
        146,59 -> 932,845
        191,929 -> 423,929
        316,409 -> 802,409
        208,560 -> 559,209
        885,237 -> 135,987
        477,486 -> 260,486
        845,59 -> 845,811
        225,369 -> 162,369
        858,678 -> 858,362
        162,972 -> 27,972
        828,26 -> 283,571
        670,48 -> 114,604
        732,487 -> 620,487
        570,575 -> 14,19
        113,203 -> 162,154
        374,702 -> 374,452
        850,575 -> 535,575
        841,133 -> 841,474
        976,960 -> 642,960
        177,428 -> 177,246
        969,289 -> 589,289
        787,842 -> 731,786
        743,709 -> 336,709
        15,914 -> 299,630
        863,952 -> 17,952
        586,889 -> 586,512
        442,128 -> 436,128
        633,367 -> 79,921
        21,990 -> 257,990
        829,297 -> 829,103
        975,633 -> 879,633
        946,887 -> 72,13
        531,720 -> 123,312
        84,954 -> 815,223
        989,982 -> 257,982
        669,417 -> 928,158
        128,935 -> 87,976
        692,850 -> 191,850
        686,856 -> 686,259
        135,396 -> 473,58
        837,206 -> 629,206
        751,227 -> 751,900
        190,617 -> 190,502
        850,265 -> 254,265
        229,587 -> 325,491
        980,747 -> 465,232
        54,375 -> 439,375
        737,844 -> 711,844
        533,219 -> 123,629
        232,805 -> 232,798
        911,441 -> 911,160
        80,294 -> 80,527
        880,533 -> 590,533
        674,84 -> 674,670
        956,440 -> 554,842
        24,939 -> 890,73
        516,183 -> 145,554
        71,584 -> 71,766
        629,173 -> 643,187
        34,360 -> 639,965
        983,871 -> 983,682
        986,590 -> 986,327
        769,986 -> 130,986
        392,192 -> 70,192
        577,379 -> 635,379
        243,664 -> 162,664
        273,987 -> 273,192
        251,548 -> 558,855
        989,736 -> 989,611
        400,697 -> 134,431
        646,923 -> 646,841
        768,782 -> 386,782
        93,973 -> 939,127
        489,91 -> 489,551
        313,683 -> 248,748
        986,61 -> 201,846
        322,413 -> 737,413
        567,716 -> 567,614
        198,624 -> 439,624
        402,198 -> 147,453
        897,352 -> 897,298
        773,379 -> 773,19
        373,256 -> 931,814
        690,796 -> 543,796
        884,368 -> 464,368
        136,864 -> 622,378
        458,569 -> 458,254
        491,462 -> 491,412
        558,340 -> 73,340
        980,52 -> 980,605
        126,609 -> 390,345
        437,659 -> 17,659
        53,928 -> 982,928
        389,591 -> 389,832
        464,46 -> 464,754
        646,680 -> 646,988
        919,159 -> 109,969
        334,75 -> 219,75
        976,639 -> 976,685
        264,773 -> 128,773
        787,771 -> 699,771
        415,124 -> 549,124
        468,71 -> 468,701
        815,121 -> 797,121
        619,95 -> 610,104
        886,294 -> 120,294
        148,136 -> 148,314
        816,971 -> 454,971
        888,733 -> 431,733
        59,836 -> 840,55
        52,965 -> 962,55
        989,982 -> 19,12
        697,818 -> 185,306
        883,638 -> 481,638
        429,285 -> 170,26
        516,507 -> 516,301
        767,102 -> 61,808
        764,793 -> 209,238
        568,411 -> 261,718
        706,622 -> 685,622
        226,110 -> 790,674
        544,429 -> 544,334
        794,588 -> 794,792
        804,738 -> 782,738
        370,552 -> 370,189
        960,275 -> 644,275
        133,896 -> 686,896
        12,986 -> 987,11
        978,973 -> 69,64
        92,465 -> 62,465
        733,57 -> 18,57
        110,845 -> 110,272
        123,935 -> 123,499
        37,960 -> 986,11
        332,209 -> 344,221
        237,279 -> 349,279
        875,635 -> 875,420
        552,174 -> 552,635
        10,93 -> 853,936
        909,82 -> 909,926
        511,743 -> 511,830
        223,974 -> 223,124
        829,543 -> 11,543
        307,671 -> 206,570
        126,72 -> 956,72
        528,903 -> 528,223
        644,524 -> 952,216
        734,324 -> 734,105
        225,558 -> 225,159
        667,122 -> 667,64
        582,93 -> 582,509
        817,932 -> 727,932
        898,18 -> 79,837
        12,987 -> 986,13
        426,79 -> 722,79
        496,884 -> 906,884
        953,183 -> 953,508
        360,881 -> 975,881
        765,862 -> 579,862
        14,55 -> 14,560
        454,333 -> 290,333
        19,479 -> 91,551
        696,41 -> 56,41
        329,203 -> 812,203
        498,559 -> 498,636
        822,852 -> 614,852
        410,370 -> 410,624
        829,415 -> 805,415
        775,980 -> 204,980
        705,780 -> 116,191
        49,30 -> 988,969
        324,199 -> 554,199
        727,572 -> 157,572
        212,693 -> 93,693
        886,105 -> 152,105
        239,834 -> 958,115
        623,920 -> 623,523
        389,225 -> 106,508
        443,426 -> 443,108
        129,770 -> 858,41
        906,559 -> 392,559
        44,793 -> 774,793
        693,275 -> 693,738
        623,434 -> 184,873
        774,623 -> 774,895
        140,187 -> 140,238
        247,503 -> 45,301
        575,365 -> 950,365
        101,120 -> 646,120
        42,682 -> 649,75
        749,767 -> 516,534
        551,53 -> 73,531
        15,26 -> 885,896
        749,15 -> 235,529
        548,169 -> 784,405
        458,564 -> 962,564
        663,873 -> 678,873
        349,773 -> 349,927
        777,180 -> 637,320
        238,306 -> 844,912
        927,818 -> 652,543
        404,673 -> 952,125
        750,297 -> 18,297
        926,958 -> 926,669
        767,843 -> 767,833
        151,136 -> 234,219
        927,789 -> 468,330
        593,361 -> 593,447
        48,14 -> 954,920
        282,972 -> 790,972
        537,446 -> 202,446
        847,125 -> 357,615
        667,609 -> 299,609
        820,987 -> 359,987
        342,889 -> 595,889
        692,414 -> 239,414
        916,935 -> 70,89
        289,884 -> 289,790
        264,562 -> 373,562
        850,24 -> 126,748
        877,159 -> 213,823
        702,607 -> 702,454
        432,883 -> 432,260
        530,387 -> 229,387
        783,39 -> 783,933
        757,775 -> 757,81
        416,376 -> 474,376
        220,462 -> 220,824
        438,317 -> 421,317
        403,312 -> 866,312
        902,923 -> 204,923
        345,33 -> 819,33
        376,521 -> 549,521
        172,320 -> 129,277
        25,975 -> 976,24
        730,108 -> 465,373
        607,468 -> 737,598
        376,55 -> 672,55
        807,113 -> 974,113
        345,804 -> 695,454
        687,921 -> 650,884
        262,743 -> 262,753
        889,734 -> 499,344
        424,727 -> 909,242
        100,957 -> 100,832
        558,958 -> 376,958
        422,473 -> 539,356
        424,463 -> 158,463
        329,543 -> 816,543
        300,74 -> 362,136
        620,691 -> 620,312
        215,727 -> 360,582
        692,116 -> 618,116
        945,722 -> 945,560
        851,83 -> 450,484
        692,424 -> 254,862
        160,214 -> 160,405
        937,101 -> 854,184
        989,14 -> 18,985
        256,275 -> 828,847
        797,748 -> 509,748
        521,148 -> 422,148
        85,549 -> 85,807
        689,688 -> 443,442
        750,664 -> 648,562
        51,616 -> 51,54
        925,272 -> 925,696
        284,560 -> 369,560
        509,685 -> 509,559
        985,157 -> 273,869
        570,765 -> 614,721
        62,981 -> 985,58
        289,496 -> 289,104
        752,232 -> 692,292
        82,948 -> 683,948
        15,20 -> 984,989
        252,950 -> 252,132
        930,659 -> 614,659
        552,449 -> 798,695
        850,894 -> 342,386
        412,465 -> 412,383
        249,616 -> 351,718
        759,289 -> 613,289
        673,347 -> 673,842
        749,493 -> 449,493
        378,468 -> 378,674
        914,924 -> 890,900
        514,56 -> 606,56
        855,233 -> 979,233
        170,756 -> 170,961
        450,601 -> 450,87
        868,192 -> 125,935
        702,137 -> 231,608
        109,36 -> 632,36
        511,472 -> 511,945
        208,884 -> 923,169
        831,66 -> 146,66
        435,133 -> 884,133
        900,418 -> 916,418
        957,104 -> 127,104
        608,892 -> 608,40
        554,782 -> 55,782
        305,260 -> 305,712
        942,143 -> 226,859
        823,778 -> 317,778
        228,415 -> 228,445
        313,505 -> 669,505
        43,539 -> 43,187
        14,84 -> 743,813
        687,101 -> 277,101
        549,977 -> 549,392
        21,637 -> 214,637
        950,961 -> 104,115
        778,831 -> 958,831
        214,765 -> 579,765
        586,42 -> 89,42
        505,950 -> 505,115
        144,734 -> 144,813
        11,349 -> 11,681
        49,336 -> 99,386
        560,187 -> 560,551
        678,602 -> 761,519
        131,515 -> 411,795
        957,835 -> 957,106
        948,852 -> 948,990
        541,946 -> 541,405
        355,147 -> 724,516
        644,476 -> 625,476
        789,818 -> 207,236
        259,57 -> 431,57
        441,375 -> 441,34
        774,121 -> 882,13
        655,397 -> 188,864
        467,432 -> 235,200
        268,121 -> 268,842
        975,14 -> 11,978
        124,904 -> 935,93
        401,582 -> 420,582
        170,700 -> 523,347
        20,681 -> 20,174
        420,939 -> 173,692
        61,933 -> 956,38
        686,458 -> 686,939
        780,561 -> 305,86
        792,644 -> 792,780
        632,550 -> 938,550
        441,252 -> 841,252
        789,59 -> 789,418
        981,11 -> 278,714
        264,41 -> 264,186
        870,833 -> 605,568
        160,905 -> 160,783
        385,191 -> 385,403
        774,791 -> 69,86
        409,967 -> 409,173
        868,41 -> 868,235
        536,497 -> 949,497
        757,119 -> 156,720
        563,706 -> 883,706
        124,482 -> 14,482
        353,655 -> 904,104
        194,868 -> 194,649
        810,736 -> 748,736
        815,578 -> 50,578
        531,131 -> 241,131
        18,972 -> 977,13
        761,747 -> 73,59
        650,701 -> 930,701
        470,237 -> 470,740
        333,803 -> 954,182
        644,667 -> 235,667
        943,766 -> 299,766
        985,876 -> 985,503
        170,924 -> 467,924
        249,19 -> 981,751
        462,666 -> 462,651
        404,228 -> 877,228
        174,440 -> 174,847
        910,596 -> 672,596
        430,663 -> 734,663
        711,294 -> 69,294
        193,302 -> 257,302
        959,20 -> 13,966
        171,561 -> 171,953
        704,986 -> 29,311
        285,886 -> 285,260
        945,872 -> 531,458
        265,748 -> 478,748
        26,537 -> 26,851
        205,210 -> 917,922
        590,488 -> 241,139
        536,179 -> 247,179
    """.trimIndent().lines()
}
