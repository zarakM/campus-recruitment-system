package pk.zarsh.campus

/**
 * Created by HP on 9/13/18.
 */
class jobs_profile(id:String?,jobid:String?,name:String?,description:String?, company:String?, salary:String?) {
    var name:String?
    var description:String?
    var company:String?
    var salary:String?
    var id:String?
    var jobid:String?

    init {
        this.name = name
        this.description = description
        this.company = company
        this.salary = salary
        this.id = id
        this.jobid = jobid
    }
    constructor() : this("","","","","","")
}