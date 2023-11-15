package com.example.s1af.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(
    @PrimaryKey(autoGenerate = false)
    var usernmae: String = "",
    var avatarUrl: String? = null,
    

)
