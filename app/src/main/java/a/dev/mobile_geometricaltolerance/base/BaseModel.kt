package a.dev.mobile_geometricaltolerance.base

 class BaseModel(
     internal val _id: Int,
     internal val title: String,
     internal val folder: String,
     val image: String
 )

{

    override fun toString(): String {
        return "BaseModel(\n_id=$_id, \ntitle='$title', \nfolder='$folder', \nimage='$image')"
    }
}