package pk.zarsh.campus
/**
 * Created by HP on 9/13/18.
 */

class student_profile(id:String?, name:String?,age:String?, degree:String?, cgpa:String?,experience:String?,type:String?) {
    var id :String?
    var name:String?
    var age:String?
    var degree:String?
    var cgpa:String?
    var experience:String?
    var type:String?
    var delete:String

    init {
        this.id = id
        this.name = name
        this.age = age
        this.degree = degree
        this.cgpa = cgpa
        this.experience = experience
        this.type = type
        this.delete = "no"
    }
    constructor() : this("","","","","","","")
}