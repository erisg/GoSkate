package go.skatebogota.goskate.dao

import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.data.persistence.PostEntity
import go.skatebogota.goskate.util.GenericDataTransferObject

object PostDao : GenericDataTransferObject<PostEntity, PostVO>() {

    override fun dataToObject(entity: PostEntity): PostVO =
        PostVO(
            entity.postId,
            entity.postFilePath,
            entity.description,
            entity.spot,
            entity.spotImage
        )


    override fun objectToData(objectVO: PostVO): PostEntity =
        PostEntity(
            objectVO.postId!!,
            objectVO.postFilePath!!,
            objectVO.description!!,
            objectVO.spot,
            objectVO.spotImage!!
        )

}