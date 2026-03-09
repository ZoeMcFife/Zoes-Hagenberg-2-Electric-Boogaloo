extends Car

@onready
var t : Timer = Timer.new()

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	$TurretSound.volume_db = -60
	add_child(t)
	t.timeout.connect(_on_timeout)
	t.wait_time = 1
	t.start()
	
func _on_timeout() -> void:
	shoot()
	
	t.start()
