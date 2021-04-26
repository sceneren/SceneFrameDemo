package wiki.scene.lib_common.app

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Created by zlx on 2020/9/28 10:00
 * Email: 1170762202@qq.com
 * Description:
 */
class AppContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        var mContext = context
        if (context == null) {
            return false
        }
        mContext= mContext!!.applicationContext
        return if(mContext is Application){
            AppProvider.init(context as Application)
            true
        }else{
            false
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}