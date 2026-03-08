@tool
class_name Car

extends Node3D

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
	

func rotate_wheels(speed : float, delta : float) -> void:
	wheel_fl.rotate_x(speed * delta)
	wheel_rl.rotate_x(speed * delta)
	wheel_fr.rotate_x(speed * delta)
	wheel_rr.rotate_x(speed * delta)

func turn_straight() -> void:
	wheel_fl.rotation.y = 0
	wheel_fr.rotation.y = -180
	steering_wheel.rotation.y = 0

func turn_right() -> void:
	wheel_fl.rotation.y = -30
	wheel_fr.rotation.y = -180 - 30
	steering_wheel.rotation.y = -50

func turn_left() -> void:
	wheel_fl.rotation.y = 30
	wheel_fr.rotation.y = -180 + 30
	steering_wheel.rotation.y = 50
