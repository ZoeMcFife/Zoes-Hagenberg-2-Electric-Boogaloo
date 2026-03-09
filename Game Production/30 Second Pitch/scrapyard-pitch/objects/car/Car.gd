class_name Car

extends Node3D

@export_category("Doors")

@onready var door_animation_player: AnimationPlayer = $DoorAnimationPlayer
@onready var bonnet_animation_player: AnimationPlayer = $BonnetAnimationPlayer
@onready var trunk_animation_player: AnimationPlayer = $TrunkAnimationPlayer

@export
var door_open: bool = false:
	set(value):
		if value == door_open:
			return
		
		door_open = value
		
		if not is_node_ready():
			return
		
		if value:
			door_animation_player.play("OpenDoor")
		else:
			door_animation_player.play("CloseDoor")


@export
var bonnet_open: bool = false:
	set(value):
		if value == bonnet_open:
			return
		
		bonnet_open = value
		
		if not is_node_ready():
			return
		
		if value:
			bonnet_animation_player.play("OpenBonnet")
		else:
			bonnet_animation_player.play("CloseBonnet")


@export
var trunk_open: bool = false:
	set(value):
		if value == trunk_open:
			return
		
		trunk_open = value
		
		if not is_node_ready():
			return
		
		if value:
			trunk_animation_player.play("OpenTrunk")
		else:
			trunk_animation_player.play("CloseTrunk")

@export_category("Wheels")

@export_range(0, 100, 1, "or_greater", "or_less")
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
			if !turning_left:
				turn_straight()

@export
var turning_left : bool :
	set(value):
		turning_left = value
		
		if value:
			turn_left()
		else:
			if !turning_right:
				turn_straight()

@export_category("Projectile")

@export
var projectile_scene : PackedScene

@export
var projectile_start : Marker3D

@export
var projectile_sound : AudioStreamPlayer

@export
var furry : Sprite3D

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	door_open = false
	bonnet_open = false
	trunk_open = false
	turn_straight()

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	rotate_wheels(wheel_speed, delta)
	

var steer_angle: float = 0.0
var wheel_spin: float = 0.0

func rotate_wheels(speed: float, delta: float) -> void:
	wheel_spin += speed * delta

	#wheel_fl.rotate_object_local(Vector3.RIGHT, spin)
	#wheel_fr.rotate_object_local(Vector3.RIGHT, spin)
	#wheel_rl.rotate_object_local(Vector3.RIGHT, spin)
	#wheel_rr.rotate_object_local(Vector3.RIGHT, spin)

	update_front_wheels()
	update_rear_wheels()

func shoot() -> void:
	var projectile : RigidBody3D = projectile_scene.instantiate()
	get_parent_node_3d().get_parent_node_3d().add_child(projectile)
	projectile.global_position = projectile_start.global_position
	projectile.global_rotation = projectile_start.global_rotation
	projectile.apply_impulse(projectile.global_transform.basis.z * 100)
	projectile_sound.play()
	
func apply_steering(angle_deg: float) -> void:
	steer_angle = deg_to_rad(angle_deg)

	update_front_wheels()

	var steering_angle_rad: float = deg_to_rad(angle_deg * 1.6)
	steering_wheel.transform.basis = Basis(Vector3.UP, steering_angle_rad)


func update_front_wheels() -> void:
	var steer_basis := Basis(Vector3.UP, steer_angle)
	var spin_basis := Basis(Vector3.RIGHT, wheel_spin)

	var final_basis := steer_basis * spin_basis

	wheel_fl.transform.basis = final_basis
	wheel_fr.transform.basis = final_basis


func update_rear_wheels() -> void:
	var spin_basis := Basis(Vector3.RIGHT, wheel_spin)

	wheel_rl.transform.basis = spin_basis
	wheel_rr.transform.basis = spin_basis

func turn_straight() -> void:
	apply_steering(0.0)

func turn_right() -> void:
	apply_steering(-30.0)

func turn_left() -> void:
	apply_steering(30.0)
