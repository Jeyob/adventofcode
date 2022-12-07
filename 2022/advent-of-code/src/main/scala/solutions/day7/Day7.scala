package solutions.day7

import scala.io.Source
object Day7 {
  private def printFileStructure(node: Node, depth: Int = 0): Unit = {
    if (node.isDirectory) {
      println(s"""${"\t".repeat(depth)} ${node.getName} (dir)""")
      node.getChildren.foreach(c => printFileStructure(c, depth+1))
    } else {
      println(s"""${"\t".repeat(depth)} ${node.getName} (file, size=${node.getSize})""")
    }
  }

  private def fsSize(node: Node): Int = {
    if (!node.isDirectory) {
      return node.getSize
    }
    node.getChildren.foldLeft(0) { (acc, child) =>
      acc + fsSize(child)
    }
  }
  private def findDirsWithMaxSize(root: Node, sizeThreshold: Int): List[(Node, Int)] = {
    var foundDirs = List[(Node, Int)]()
    def dfsFind(node: Node): Int = {
      if (!node.isDirectory) { return node.getSize }

      val res = node.getChildren.foldLeft(0) { (acc, child) =>
        acc + dfsFind(child)
      }

      if (res <= sizeThreshold) {
        foundDirs = (node, res) :: foundDirs
      }
      res
    }
    dfsFind(root)
    foundDirs
  }
  def main(args: Array[String]): Unit = {

    val bufferedSource = Source.fromFile("src/main/scala/solutions/day7/input.txt")
    var rootNode: Node = null
    var currNode: Node = null

    bufferedSource.getLines().foreach { line =>
      val cmdLst = line.split(' ').toList
      cmdLst.head match {
        case "$" =>
          cmdLst match {
            case List(_, cmd, dir) => // cd
              if (cmd == "cd") {
                if (dir == "..") {
                  currNode = currNode.getParent match {
                    case Some(p) => p
                    case None => currNode
                  }
                } else {
                  if (rootNode == null) {
                    rootNode = Node(dir, "dir")
                    currNode = rootNode
                  } else {
                    currNode = currNode.getChild(dir) match {
                      case Some(n) => n
                      case _ => throw new MatchError(s"trying to cd to no existing folder $dir")
                    }
                  }
                }
              }
            case List(_, cmd) => // ls
              if (cmd != "ls") {
                throw new MatchError(s"should be ls but got $cmd")
              }
          }
        case _ =>
          val List(sizeOrDir, name) = cmdLst

          if (sizeOrDir == "dir") {
            currNode.addChild(Node(name = name, nType = "dir", parent = currNode))
          } else {
            currNode.addChild(Node(name = name, nType = "file", size = sizeOrDir.toInt, parent = Some(currNode)))
          }
      }
    }

    printFileStructure(rootNode, 0)

    // problem 1
    println(findDirsWithMaxSize(rootNode, 100000).foldLeft(0) { (acc, item) => acc + item._2 })

    // problem 2
    val rootDirSize = fsSize(rootNode)
    println(s"rootDirSize: $rootDirSize")
    val unusedSpace = 70000000 - rootDirSize
    println(s"unusedSpace: $unusedSpace")
    val diffSpace = 30000000 - unusedSpace
    println(s"diffSpace: $diffSpace")

    val (dirToDelete, dirSize) = findDirsWithMaxSize(rootNode, rootDirSize)
      .filter(x => x._2 >= diffSpace)
      .sortWith(_._2 < _._2)
      .head

    println(s"removing ${dirToDelete.getName} of size: ${dirSize}")

    bufferedSource.close()
  }
}
