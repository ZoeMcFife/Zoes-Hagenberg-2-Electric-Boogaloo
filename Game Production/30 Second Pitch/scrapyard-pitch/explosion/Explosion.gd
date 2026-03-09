@tool
class_name Explosion

extends Node2D

@export
var sprite : AnimatedSprite2D

@export
var audio_player : AudioStreamPlayer

@export_tool_button("Explode", "Callable")
var explosion_callable : Callable = start_explosion

@export
var explode_anim : bool:
	set(value):
		start_explosion()

func _ready() -> void:
	sprite.play("explode", 0)

func start_explosion() -> void:
	sprite.play("explode")
	audio_player.play()
	
