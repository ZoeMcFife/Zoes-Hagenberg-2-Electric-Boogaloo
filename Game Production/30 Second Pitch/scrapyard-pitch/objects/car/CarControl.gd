class_name CarControl
extends RigidBody3D

@export_category("Car Control")

@export var active : bool = true :
	set(value):
		$Camera3D.current = active
		
@export var car : Car

@export var acceleration_force : float = 4500.0
@export var brake_force : float = 3500.0
@export var turn_force : float = 1200.0

@export var sideways_friction : float = 8.0
@export var air_control : float = 0.15

@export var ground_ray_cast : ShapeCast3D

@export_category("Suspension")

@export var suspension_rays : Array[RayCast3D]

@export var suspension_rest_length : float = 0.5
@export var suspension_strength : float = 18000.0
@export var suspension_damping : float = 1800.0

func _process(delta: float) -> void:
	_apply_suspension(delta)

func _physics_process(delta: float) -> void:
	if !active:
		Input.mouse_mode = Input.MOUSE_MODE_VISIBLE
		return
	
	Input.mouse_mode = Input.MOUSE_MODE_CAPTURED

	var forward : Vector3 = global_transform.basis.z
	var right : Vector3 = global_transform.basis.x

	var forward_speed = linear_velocity.dot(forward)
	var sideways_speed = linear_velocity.dot(right)

	car.wheel_speed = forward_speed

	var grounded = ground_ray_cast.is_colliding()

	# -------- ACCELERATION --------
	if grounded:

		if Input.is_action_pressed("drive_forwards"):
			apply_central_force(forward * acceleration_force)
			
			if !$AudioStreamPlayer.playing:
				$AudioStreamPlayer.play()

		if Input.is_action_pressed("drive_backwards"):
			apply_central_force(-forward * brake_force)

			if !$AudioStreamPlayer.playing:
				$AudioStreamPlayer.play()

	else:
		# small air control
		if Input.is_action_pressed("drive_forwards"):
			apply_central_force(forward * acceleration_force * air_control)


	# -------- STEERING --------
	if grounded:

		var steer_strength = clamp(forward_speed * 0.4, 0.0, 1.0)

		if Input.is_action_pressed("turn_left"):
			apply_torque(Vector3.UP * turn_force * steer_strength)
			car.turning_left = true
		else:
			car.turning_left = false

		if Input.is_action_pressed("turn_right"):
			apply_torque(Vector3.UP * -turn_force * steer_strength)
			car.turning_right = true
		else:
			car.turning_right = false


	# -------- SIDEWAYS FRICTION (SUPER IMPORTANT) --------
	if grounded:
		var sideways_force = -right * sideways_speed * sideways_friction
		apply_central_force(sideways_force)


	# -------- INTERACTIONS --------
	if Input.is_action_just_pressed("open_bonnet"):
		car.bonnet_open = !car.bonnet_open

	if Input.is_action_just_pressed("open_door"):
		car.door_open = !car.door_open

	if Input.is_action_just_pressed("open_trunk"):
		car.trunk_open = !car.trunk_open
	
	if Input.is_action_just_pressed("shoot_car"):
		car.shoot()
	
func _apply_suspension(delta):
	for ray in suspension_rays:

		if !ray.is_colliding():
			continue

		var hit_point = ray.get_collision_point()
		var ray_origin = ray.global_transform.origin

		var distance = ray_origin.distance_to(hit_point)

		var compression = suspension_rest_length - distance

		if compression <= 0:
			continue

		# spring force
		var spring_force = compression * suspension_strength

		# damping
		var vel = linear_velocity.dot(global_transform.basis.y)
		var damping_force = vel * suspension_damping

		var force = (spring_force - damping_force)

		apply_force(Vector3.UP * force, ray_origin - global_transform.origin)
