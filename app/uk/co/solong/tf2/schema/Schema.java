
package uk.co.solong.tf2.schema;


public class Schema{
    private Long schemaVersion;
   	private Result result;

 	public Result getResult(){
		return this.result;
	}
	public void setResult(Result result){
		this.result = result;
	}
    public Long getSchemaVersion() {
        return schemaVersion;
    }
    public void setSchemaVersion(Long schemaVersion) {
        this.schemaVersion = schemaVersion;
    }
}
