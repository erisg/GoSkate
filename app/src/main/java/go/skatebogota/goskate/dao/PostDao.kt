package go.skatebogota.goskate.dao

import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.data.persistence.PostEntity
import go.skatebogota.goskate.util.GenericDataTransferObject

object PostDao : GenericDataTransferObject<PostEntity, PostVO>() {
    override fun dataToObject(entity: PostEntity): PostVO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun objectToData(objectVO: PostVO): PostEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}