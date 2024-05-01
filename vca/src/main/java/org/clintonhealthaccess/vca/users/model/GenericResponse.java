package org.clintonhealthaccess.vca.users.model;

public class GenericResponse {
	
	private String message;
    private String result;
  
  
    public GenericResponse(String result, String message) {
        super();
        this.message = message;
        this.result = result;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
    
    

}
