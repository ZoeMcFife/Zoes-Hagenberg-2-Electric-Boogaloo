class_name CarControl

extends RigidBody3D

@export_category("Car Control")

@export
var active : bool = false

@export
var car : Car

@export
var accelleration_force : float

@export
var brake_force : float

@export
var turn_force : float

@export
var ground_ray_cast : ShapeCast3D

func _physics_process(delta: float) -> void:
	car.wheel_speed = (linear_velocity * transform.inverse()).z
	
	if !active:
		return
	
	if !ground_ray_cast.is_colliding():
		return
	
	if Input.is_action_pressed("drive_forwards"):
		apply_central_force(Vector3(0, 0, accelleration_force) * transform.inverse())
	
	if Input.is_action_pressed("drive_backwards"):
		apply_central_force(Vector3(0, 0, brake_force) * transform.inverse())
		
	if Input.is_action_pressed("turn_left"):
		apply_torque(Vector3(0, turn_force * absf(car.wheel_speed * 2), 0) * transform.inverse())
		car.turning_left = true
		
	else:
		car.turning_left = false

	if Input.is_action_pressed("turn_right"):
		apply_torque(Vector3(0, -turn_force * absf(car.wheel_speed), 0) * transform.inverse())
		car.turning_right = true
	else:
		car.turning_right = false
	
	if Input.is_action_just_pressed("open_bonnet"):
		car.bonnet_open = !car.bonnet_open
	
	if Input.is_action_just_pressed("open_door"):
		car.door_open = !car.door_open
		
	if Input.is_action_just_pressed("open_trunk"):
		car.trunk_open = !car.trunk_open
