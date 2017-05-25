package io.elastest.etm.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="ELAS_ETM_PROJECT")
@NamedQuery(name="Project.findAll", query="SELECT e FROM Project e")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	@JsonProperty("id")
	private Long id = null;
	
	@JsonProperty("name")
	private String name = null;
	
	@JsonProperty("tJobs")
	//bi-directional many-to-one association to ElasEtmTjobexec
	@OneToMany(mappedBy="project")
	private List<TJob> tJobs;
	
	@JsonProperty("tOJobs")
	//bi-directional many-to-one association to ElasEtmTjobexec
	@OneToMany(mappedBy="project")
	private List<TOJob> tOJobs;
	
	@JsonProperty("suts")
	//bi-directional many-to-one association to ElasEtmTjobexec
	@OneToMany(mappedBy="project")
	private List<SutSpecification> suts;
	
	public Project() {}
	
	public Project(Long id, String name, List<TJob> tJobs, List<TOJob> tOJobs, List<SutSpecification> suts){
		this.id = id==null? 0: id;
		this.name = name;
		this.tJobs = tJobs;
		this.tOJobs = tOJobs;
		this.suts = suts;
		
	}

	public Project id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 * 
	 * @return id
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id==null? 0: id;
	}	

	public Project id(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Get name
	 * 
	 * @return name
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Project name(String name) {
		this.name = name;
		return this;
	}	
	
	public List<TJob> getTJobs() {
		return this.tJobs;
	}

	public void setTJobs(List<TJob> tJobs) {
		this.tJobs = tJobs;
	}
	
	public Project tJobs(List<TJob> tJobs) {
		this.tJobs = tJobs;
		return this;
	}
	
	public List<TOJob> getTOJob() {
		return this.tOJobs;
	}

	public void setTOJobs(List<TOJob> tOJobs) {
		this.tOJobs = tOJobs;
	}
	
	public Project tOJobs(List<TOJob> tOJobs) {
		this.tOJobs = tOJobs;
		return this;
	}
		
	public List<SutSpecification> getSuts() {
		return this.suts;
	}

	public void setSuts(List<SutSpecification> suts) {
		this.suts = suts;
	}
	
	public Project suts(List<SutSpecification> suts) {
		this.suts = suts;
		return this;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Project project = (Project) o;
		return Objects.equals(this.name, project.name)  && Objects.equals(this.id, project.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DeployConfig {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");		
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}