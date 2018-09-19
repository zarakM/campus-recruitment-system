package pk.zarsh.campus

/**
 * Created by HP on 9/13/18.
 */
class company_profile( id:String?,name:String?,founded:String?, employers:String?, address:String?,type:String?) {
    var id:String?
    var name:String?
    var founded:String?
    var employers:String?
    var address:String?
    var type:String?
    var delete:String

    init {
        this.id=id
        this.name = name
        this.founded = founded
        this.employers = employers
        this.address = address
        this.type = type
        this.delete = "no"
    }
    constructor(delete:String):this()
    constructor() : this("","","","","","")
}