package io.warehouse13.authorizationserver.service.impl;

import io.warehouse13.authorizationserver.io.entity.RoleEntity;
import io.warehouse13.authorizationserver.io.entity.UserEntity;
import io.warehouse13.authorizationserver.io.repository.RoleRepository;
import io.warehouse13.authorizationserver.io.repository.UserRepository;
import io.warehouse13.authorizationserver.service.UserService;
import io.warehouse13.authorizationserver.shared.dto.MessageDto;
import io.warehouse13.authorizationserver.shared.dto.UserDto;
import io.warehouse13.authorizationserver.shared.enums.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


// public class UserDetailsServiceImpl implements UserDetailsService {
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with username :" + username));
	}

	@Override
	public MessageDto createUser(UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Set<RoleEntity> roles = new HashSet<>();
		userDto.getRoles().forEach(role -> {
			RoleEntity stored = roleRepository
					.findByRole(RoleName.valueOf(role.getRole().name()))
					.orElseThrow(() -> new RuntimeException("Role with name " + (role.getRole().name() + " not found")));
			roles.add(stored);
		});
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setRoles(roles);
		UserEntity stored = userRepository.save(userEntity);
		return new MessageDto("User with username " + stored.getUsername() + " successfully registered");
	}
}
