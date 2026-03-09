@tool
class_name PreviewRect

extends ColorRect

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	if !Engine.is_editor_hint():
		hide()
		queue_free()
		
	size.x = 640
	size.y = 360

func _process(delta: float) -> void:
	size.x = 640
	size.y = 360
	color.a = 0.1
