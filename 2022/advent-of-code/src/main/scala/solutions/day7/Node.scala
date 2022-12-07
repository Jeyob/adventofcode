package solutions.day7

class Node (name: String, nType: String = "dir", fileSize: Int, parent: Option[Node]) {
  private val _parent: Option[Node] = parent
  private var _children: List[Node] = List.empty[Node]
  private val _name: String = name
  private var _size: Int = fileSize

  def isDirectory: Boolean = { nType == "dir" }
  // getters
  def getName: String = _name
  def getParent: Option[Node] = _parent
  def getChild(name: String): Option[Node] = {
    _children.find(x => x._name == name)
  }
  def getChildren: List[Node] = _children
  def getSize: Int = _size
  // setters
  def addChild(child: Node): Unit = {
    _children = child :: _children
  }
  def size_=(size: Int): Unit = {
    _size = size
  }
}

object Node {
  def apply(name: String, nType: String, size: Int, parent: Option[Node]): Node = {
    new Node(name = name, nType = nType, fileSize = size, parent = parent)
  }

  def apply(name: String, nType: String, parent: Node): Node = {
    apply(name = name, nType = nType, size = 0, parent = Some(parent))
  }
  def apply(name: String, nType: String): Node = {
    apply(name = name, nType = nType, size = 0, parent = None)
  }
}
