package a.dev.mobile_geometricaltolerance.utils

import a.dev.mobile_geometricaltolerance.base.BaseModel
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AssetDBOpenHelper(private val context: Context) : SQLiteOpenHelper(
    context, DB_NAME, null,
    DB_VERSION
) {

    internal val TAG = "AssetDBOpenHelper"
    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {

        const val DB_VERSION = 1
        const val DB_NAME = "geometrical_tolerance.db"
        /********************************/
        const val DB_TABLE = "geom_tol"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_FOLDER = "folder"
        const val COLUMN_TYPE = "type"

        //      независимые
        //1 допуск формы
        //2 допуск расположения
        //3 суммарный допуск расположения и формы

        /********************************/

    }
    /*Then open or create database in your activity

    val adb = AssetDatabaseOpenHelper(this)
    adb.openDatabase()*/

    fun openDatabase(): SQLiteDatabase {
        val dbFile = context.getDatabasePath(DB_NAME)

        if (!dbFile.exists()) {
            try {
                val checkDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null)
                checkDB?.close()
                copyDatabase(dbFile)
            } catch (e: IOException) {
                throw RuntimeException("error", e)
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    @SuppressLint("WrongConstant")
    private fun copyDatabase(dbFile: File) {
        val `is` = context.assets.open(DB_NAME)
        val os = FileOutputStream(dbFile)
        val buffer = ByteArray(1024)
        while (`is`.read(buffer) > 0) {
            os.write(buffer)
        }
        os.flush()
        os.close()
        `is`.close()
    }

    @SuppressLint("Recycle")
    fun getDataDB(type: Int): ArrayList<BaseModel> {

        val modelData = ArrayList<BaseModel>()
        val db = writableDatabase
        val selectQuery = "select * from $DB_TABLE where $COLUMN_TYPE = $type"
        val cursor: Cursor?
        cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {

                modelData.add(
                    BaseModel(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),

                        cursor.getString(cursor.getColumnIndex(COLUMN_FOLDER)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                    )

                )

                cursor.moveToNext()
            }
            cursor.close()
        }
        return modelData
    }
}
