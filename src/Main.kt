import java.util.*

/**
 * Created by yaroslav on 05.03.17.
 */
public  fun main(args: Array<String>) {
	println("""Use Int value.
			A or add for add
			S or search for serch
			D or delete for delete
			P or print for print""")
	val tree = RBT<Int, Int>()
	val printer = RBTPrinter<Int, Int>()
	mainLoop@ while (true) {
		val line = readLine()
		when (line) {
			null -> {
				println("Input is null. Try again.")
				continue@mainLoop
			}
			else -> {
				val splited: List<String> = line.trim().split(" ")
				val key: Int?
				when  {
					splited.size < 2 -> key = null
					else -> {
						try {
							key = splited[1].toInt()
						}
						catch (exception: NumberFormatException) {
							println("Invalid key, try again.")
							continue@mainLoop
						}
					}
				}
				when (splited[0]) {
					"A", "add" -> {
						if (key == null) {
							println("Key is null. Try again.")
							continue@mainLoop
						}
						tree.insert(key, key)
					}
					"S", "search" -> {
						if (key == null) {
							println("Key is null. Try again.")
							continue@mainLoop
						}
						println(tree.search(key))
					}
					"D", "delete" -> {
						if (key == null) {
							println("Key is null. Try again.")
							continue@mainLoop
						}
						tree.delete(key)
					}
					"P", "print" -> {
						printer.print(tree)
					}
					"Q", "quit" -> break@mainLoop
					else -> {
						println("Invalid operation with tree. Try again.")
						continue@mainLoop
					}
				}
			}
		}
	}
}