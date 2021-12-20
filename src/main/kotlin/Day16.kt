import Day16.PacketReader
import Day16.input
import Day16.sumVersions
import java.lang.RuntimeException

fun main() {

    val packet = PacketReader(input).read()

    val answer1 = packet.sumVersions()
    println(answer1) // 974

    val answer2 = packet.value
    println(answer2) // 180616437720
}

object Day16 {

    sealed interface Packet {
        val version: Int
        val typeId: Int
        val value: Long
    }

    data class Literal(override val version: Int, override val value: Long) : Packet {
        override val typeId: Int = 4
    }

    data class Operator(override val version: Int, override val typeId: Int, val packets: List<Packet>) : Packet {
        override val value = when (typeId) {
            0 -> packets.sumOf { it.value }
            1 -> packets.map { it.value }.reduce { a, b -> a * b }
            2 -> packets.minOf { it.value }
            3 -> packets.maxOf { it.value }
            5 -> packets.let { (p1, p2) -> if (p1.value > p2.value) 1 else 0 }
            6 -> packets.let { (p1, p2) -> if (p1.value < p2.value) 1 else 0 }
            7 -> packets.let { (p1, p2) -> if (p1.value == p2.value) 1 else 0 }
            else -> throw RuntimeException("Unexpected typeId $typeId")
        }
    }

    fun Packet.sumVersions(): Int =
        when (this) {
            is Literal -> version
            is Operator -> version + packets.sumOf { it.sumVersions() }
        }

    class PacketReader(data: String) {

        fun read(): Packet {
            val version = next(3).toInt(2)

            return when (val typeId = next(3).toInt(2)) {
                4 -> {
                    var binValue = ""
                    var next5: String
                    do {
                        next5 = next(5)
                        binValue += next5.drop(1)
                    } while (next5.startsWith("1"))
                    Literal(version, binValue.toLong(2))
                }
                else -> {
                    val subPackets = mutableListOf<Packet>()
                    when (next()) {
                        '0' -> {
                            val subPacketLength = next(15).toInt(2)
                            val end = pointer + subPacketLength
                            while (pointer != end) { subPackets.add(read()) }
                            Operator(version, typeId, subPackets)
                        }
                        else -> {
                            val subPacketCount = next(11).toInt(2)
                            repeat(subPacketCount) { subPackets.add(read()) }
                            Operator(version, typeId, subPackets)
                        }
                    }
                }
            }
        }

        private val binData = data.toBinary()
        private var pointer = 0

        private fun next(): Char = binData[pointer++]

        private fun next(count: Int): String {
            val end = pointer + count
            return binData.substring(pointer, end).also { pointer = end }
        }
    }

    fun String.toBinary(): String = this.map { it.toBinary() }.joinToString("")

    private fun Char.toBinary(): String = this.toString().toInt(16).toString(2).padStart(4, '0')

    const val input = "820D4A801EE00720190CA005201682A00498014C04BBB01186C040A200EC66006900C44802BA280104021B30070A4016980044C800B84B5F13BFF007081800FE97FDF830401BF4A6E239A009CCE22E53DC9429C170013A8C01E87D102399803F1120B4632004261045183F303E4017DE002F3292CB04DE86E6E7E54100366A5490698023400ABCC59E262CFD31DDD1E8C0228D938872A472E471FC80082950220096E55EF0012882529182D180293139E3AC9A00A080391563B4121007223C4A8B3279B2AA80450DE4B72A9248864EAB1802940095CDE0FA4DAA5E76C4E30EBE18021401B88002170BA0A43000043E27462829318F83B00593225F10267FAEDD2E56B0323005E55EE6830C013B00464592458E52D1DF3F97720110258DAC0161007A084228B0200DC568FB14D40129F33968891005FBC00E7CAEDD25B12E692A7409003B392EA3497716ED2CFF39FC42B8E593CC015B00525754B7DFA67699296DD018802839E35956397449D66997F2013C3803760004262C4288B40008747E8E114672564E5002256F6CC3D7726006125A6593A671A48043DC00A4A6A5B9EAC1F352DCF560A9385BEED29A8311802B37BE635F54F004A5C1A5C1C40279FDD7B7BC4126ED8A4A368994B530833D7A439AA1E9009D4200C4178FF0880010E8431F62C880370F63E44B9D1E200ADAC01091029FC7CB26BD25710052384097004677679159C02D9C9465C7B92CFACD91227F7CD678D12C2A402C24BF37E9DE15A36E8026200F4668AF170401A8BD05A242009692BFC708A4BDCFCC8A4AC3931EAEBB3D314C35900477A0094F36CF354EE0CCC01B985A932D993D87E2017CE5AB6A84C96C265FA750BA4E6A52521C300467033401595D8BCC2818029C00AA4A4FBE6F8CB31CAE7D1CDDAE2E9006FD600AC9ED666A6293FAFF699FC168001FE9DC5BE3B2A6B3EED060"
}
