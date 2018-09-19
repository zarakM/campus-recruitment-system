package pk.zarsh.campus
/**
 * Created by HP on 9/13/18.
 */

class applied_student(id:String?, job_id:String?) {
    var id :String?
    var job_id :String?

    init {
        this.id =id
        this.job_id =job_id
    }
    constructor() : this("","")
}