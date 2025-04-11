package mx.edu.itesca.practica14

data class Task(
    var id : String = "",
    var title : String = "",
    var description : String = ""
)
{
    fun toMap(): Map<String,String>{
        return mapOf(
            "title" to title,
            "description" to description
        )
    }
}
