package a.dev.mobile_geometricaltolerance

 class BaseModel(val _id: Int, val title: String, val info: String,val image: String,val type: Int )

{

    override fun toString(): String {
        return "BaseModel(\n_id=$_id, \ntitle='$title', \ninfo='$info', \nimage='$image')"
    }
}