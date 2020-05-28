package a.dev.mobile_geometricaltolerance.base

 class BaseModel(
     private val _id: Int,
     private val title: String,
     internal val folder: String,
     val image: String
 )

{

    override fun toString(): String {
        return "BaseModel(\n_id=$_id, \ntitle='$title', \nfolder='$folder', \nimage='$image')"
    }
}