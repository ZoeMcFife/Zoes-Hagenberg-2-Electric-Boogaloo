class_name Player
extends CharacterBody3D

# Movement
@export var walk_speed := 5.0
@export var sprint_speed := 8.0
@export var acceleration := 14.0
@export var air_acceleration := 4.0
@export var jump_velocity := 4.5

# Mouse
@export var mouse_sensitivity := 0.002
@export var mouse_smoothing := 8.0

# Headbob
@export var headbob_amount := 0.05
@export var headbob_speed := 10.0

# Shooting
@export var projectile_scene : PackedScene
@export var recoil_strength := 0.03

# Car
@export var car : CarControl

@onready var camera : Camera3D = $Camera3D
@onready var projectile_start : Marker3D = $Camera3D/ProjectileStart
@onready var projectile_sound : AudioStreamPlayer = $TurretSound

var controlling_car := false

var look_x := 0.0
var look_y := 0.0
var target_look_x := 0.0
var target_look_y := 0.0

var headbob_time := 0.0
var base_camera_pos : Vector3

func _ready():
	Input.mouse_mode = Input.MOUSE_MODE_CAPTURED
	base_camera_pos = camera.position
	print("help?")
	

func _input(event):
	print(event)

	if event is InputEventMouseMotion and not controlling_car:
		target_look_x -= event.relative.x * mouse_sensitivity
		target_look_y -= event.relative.y * mouse_sensitivity
		target_look_y = clamp(target_look_y, -1.5, 1.5)

	if Input.is_action_just_pressed("toggle_car"):
		toggle_car()

	if Input.is_action_just_pressed("shoot_gun") and not controlling_car:
		shoot()

	if Input.is_action_just_pressed("ui_cancel"):
		Input.mouse_mode = Input.MOUSE_MODE_VISIBLE

	if event is InputEventMouseButton and event.pressed:
		if Input.mouse_mode != Input.MOUSE_MODE_CAPTURED:
			Input.mouse_mode = Input.MOUSE_MODE_CAPTURED


func _physics_process(delta):

	if controlling_car:
		return

	# Smooth mouse movement
	look_x = lerp(look_x, target_look_x, mouse_smoothing * delta)
	look_y = lerp(look_y, target_look_y, mouse_smoothing * delta)

	rotation.y = look_x
	camera.rotation.x = look_y

	# Gravity
	if not is_on_floor():
		velocity += get_gravity() * delta

	# Jump
	if Input.is_action_just_pressed("ui_accept") and is_on_floor():
		velocity.y = jump_velocity

	# Movement input
	var input_dir = Input.get_vector("turn_left","turn_right","drive_forwards","drive_backwards")
	var direction = (transform.basis * Vector3(input_dir.x,0,input_dir.y)).normalized()

	var speed = walk_speed
	if Input.is_key_pressed(KEY_SHIFT):
		speed = sprint_speed

	var accel = acceleration
	if not is_on_floor():
		accel = air_acceleration

	var target_velocity = direction * speed

	velocity.x = move_toward(velocity.x, target_velocity.x, accel)
	velocity.z = move_toward(velocity.z, target_velocity.z, accel)

	move_and_slide()

	update_headbob(delta)


func update_headbob(delta):

	var horizontal_speed = Vector2(velocity.x, velocity.z).length()

	if is_on_floor() and horizontal_speed > 0.1:

		headbob_time += delta * headbob_speed * (horizontal_speed / walk_speed)

		var offset = Vector3(
			sin(headbob_time) * headbob_amount,
			abs(cos(headbob_time)) * headbob_amount,
			0
		)

		camera.position = base_camera_pos + offset

	else:
		headbob_time = 0
		camera.position = camera.position.lerp(base_camera_pos, 10 * delta)


func toggle_car():
	controlling_car = !controlling_car

	if controlling_car:
		car.active = true
		camera.current = false
		visible = false
	else:
		car.active = false
		global_position = car.global_position + Vector3(2,0,0)
		visible = true
		camera.current = true


func shoot():

	var projectile : RigidBody3D = projectile_scene.instantiate()

	get_tree().current_scene.add_child(projectile)

	projectile.global_position = projectile_start.global_position
	projectile.global_rotation = projectile_start.global_rotation

	projectile.apply_impulse(projectile.global_transform.basis.z * -30)

	projectile_sound.play()

	apply_recoil()


func apply_recoil():

	target_look_y += recoil_strength
