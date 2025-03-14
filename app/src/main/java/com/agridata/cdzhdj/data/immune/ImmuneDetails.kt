package com.agridata.cdzhdj.data.immune

/**
 * @Author      : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date        : on 2024-08-19 15:35.
 * @Description :描述
 */
data class ImmuneDetails(
    val ErrorCode: Int,
    val Message: Any,
    val Result: Result,
    val Status: Int
)

data class Result(
    val AHIUser: AHIUser,
    val AHIUserID: Any,
    val Animal: Animal,
    val AnimalID: Int,
    val Cause: Any,
    val CurrentAge: Int,
    val Dep_AnimalID: DepAnimalID,
    val DetailID: String,
    val EarTags: String,
    val FeedBackTime: Any,
    val ImmuneCount: Int,
    val ImmuneQuantity: String,
    val ImmuneType: Int,
    val Immuned: String,
    val Invoices: Any,
    val IsEarTagAnimal: Int,
    val IsSelfWrite: Int,
    val IsTransfer: Int,
    val Mid: String,
    val NotImmunedReason: Any,
    val PreLiveStock: Int,
    val Region: Region,
    val Remark: Any,
    val XDRCoreID: String,
    val XDRCoreInfo: XDRCoreInfo,
    val ReplenishEarTagCode:String,
)

data class AHIUser(
    val Key: String,
    val Name: String
)

data class Animal(
    val Key: String,
    val Name: String
)

data class DepAnimalID(
    val AnimalLevel: Int,
    val AnimalLivedays: Int,
    val AnimalName: String,
    val AnimalParentID: Int,
    val EartagCode: Int,
    val ID: Int,
    val Mid: String
)

data class Region(
    val ID: Int,
    val RI1: Int,
    val RI2: Int,
    val RI3: Int,
    val RI4: Int,
    val RI5: Int,
    val RegionCode: String,
    val RegionFullName: String,
    val RegionLevel: Int,
    val RegionName: String,
    val RegionParentID: Int
)

data class XDRCoreInfo(
    val Key: String,
    val Name: String
)