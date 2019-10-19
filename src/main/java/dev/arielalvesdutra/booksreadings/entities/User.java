package dev.arielalvesdutra.booksreadings.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String email;
		
	private String name;
	
	@JsonIgnore
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Profile> profiles = new ArrayList<Profile>();

	@JsonIgnoreProperties("reader")
	@OneToMany(mappedBy = "reader", cascade = CascadeType.ALL,  orphanRemoval = true)
	private Set<BookReading> booksReadings = new HashSet<BookReading>();
	
	public Set<BookReading> getBooksReadings() {
		return this.booksReadings;
	}

	public void setBooksReadings(Set<BookReading> booksReading) {
		this.booksReadings = booksReading;
	}

	public void addBookReading(BookReading booksReading) {
		booksReading.setReader(this);
		this.booksReadings.add(booksReading);
	}

	public void removeBookReading(BookReading booksReading) {
		this.booksReadings.remove(booksReading);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
	return "[ id: " + this.getId() 
				+ ", name: " + this.getName() 
				+ ", bookReads:" + this.getBooksReadings() + "]";
	}
}
