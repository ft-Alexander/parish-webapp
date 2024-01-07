package org.integ.proj.parishwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Role")
public class Role implements GrantedAuthority
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Staff> users = new ArrayList<>();
    
    @Override
    public String toString() {
    	return capitalizeFirstLetter(removeRolePrefix(name));
    }
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    private String removeRolePrefix(String roleName) {
        if (roleName != null && roleName.startsWith("ROLE_")) {
            return roleName.substring("ROLE_".length());
        }
        return roleName;
    }

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return "ROLE_" + name;
	}
}
