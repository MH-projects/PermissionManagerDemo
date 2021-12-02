package com.mh.permissionmanagerdemo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mh.permission_manager.*

class MainActivity : AppCompatActivity(), IPermissionResult {

    private var permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_CONTACTS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn1).setOnClickListener {
            PermissionManager(this, this).reqPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        findViewById<Button>(R.id.btn2).setOnClickListener {
            PermissionManager(this, this).reqPermissions(permissions)
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            try {
                val packageName = applicationContext.packageName
                val runtime = Runtime.getRuntime()
                runtime.exec("pm clear $packageName")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.apply {
            setCancelable(true)
            setTitle(title)
            setMessage(message)
            setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.create().show()
    }

    override fun setResult(permissionResult: PermissionResult) {

        when (permissionResult.result) {
            Result.GRANTED -> {
                showAlert(permissionResult.permissionName, "Permiso otorgado")
            }

            Result.DENIED -> {
                showAlert(permissionResult.permissionName, "Permiso denegado")
            }

            Result.DENIED_ALWAYS -> {
                showAlert(permissionResult.permissionName, "Permiso denegado por siempre")
            }
        }
    }

    override fun setResult(
        allPermissionsGranted: Boolean,
        canAskPermissions: Boolean,
        permissionsResult: List<PermissionResult>
    ) {
        if (allPermissionsGranted) {
            var permissions = ""
            permissionsResult.forEach {
                permissions += "$it \n\n"
            }
            showAlert("Todos los permisos otorgados", permissions)
        } else {

            var permissions = ""
            permissionsResult.forEach {
                permissions += "${
                    it.permissionName.replace(
                        "android.permission.",
                        ""
                    )
                } - ${it.result}\n\n"
            }
            showAlert(
                "Permisos denegados",
                "canAskPermissions: $canAskPermissions \n\n $permissions"
            )
        }
    }
}