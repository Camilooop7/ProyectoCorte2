package co.edu.unbosque.model;

public class LibroEnLineaDTO {
	
	
	private Long id;
	private String link;
	
	public LibroEnLineaDTO() {
		// TODO Auto-generated constructor stub
	}

	public LibroEnLineaDTO(String link) {
		super();
		this.link = link;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
	
	
	

}
