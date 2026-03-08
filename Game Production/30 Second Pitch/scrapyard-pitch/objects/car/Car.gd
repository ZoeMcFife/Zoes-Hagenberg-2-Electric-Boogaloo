@tool
class_name Car

extends Node3D

@export_category("Doors")



@export_category("Wheels")

@export_range(0, 100, 1)
var wheel_speed : float

@export
var wheel_fl : MeshInstance3D

@export
var wheel_fr : MeshInstance3D

@export
var wheel_rl : MeshInstance3D

@export
var wheel_rr : MeshInstance3D

@export
var steering_wheel : MeshInstance3D

@export
var turning_right : bool :
	set(value):
		turning_right = value
		
		if value:
			turn_right()
		else:
			turn_straight()

@export
var turning_left : bool :
	set(value):
		turning_left = value
		
		if value:
			turn_left()
		else:
			turn_straight()

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	pass # Replace with function body.

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	rotate_wheels(wheel_speed, delta)
	

var steer_angle: float = 0.0


func rotate_wheels(speed: float, delta: float) -> void:
	var spin: float = speed * delta

	wheel_fl.rotate_object_local(Vector3.RIGHT, spin)
	wheel_fr.rotate_object_local(Vector3.RIGHT, spin)
	wheel_rl.rotate_object_local(Vector3.RIGHT, spin)
	wheel_rr.rotate_object_local(Vector3.RIGHT, spin)


func apply_steering(angle_deg: float) -> void:
	var angle_rad: float = deg_to_rad(angle_deg)
	steer_angle = angle_rad

	var fl_basis: Basis = Basis(Vector3.UP, angle_rad)
	var fr_basis: Basis = Basis(Vector3.UP, angle_rad)

	wheel_fl.transform.basis = fl_basis
	wheel_fr.transform.basis = fr_basis

	var steering_angle_rad: float = deg_to_rad(angle_deg * 1.6)
	steering_wheel.transform.basis = Basis(Vector3.UP, steering_angle_rad)


func turn_straight() -> void:
	apply_steering(0.0)

func turn_right() -> void:
	apply_steering(-30.0)

func turn_left() -> void:
	apply_steering(30.0)
