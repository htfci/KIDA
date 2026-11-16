from enum import Enum
from pydantic import BaseModel
from src.schemas import AiFileProcessTask



class AiFileProcessDto(BaseModel):
    content: str
    task: AiFileProcessTask | None = None
    user_id: str | None = None


class AiFileProcessTask(str, Enum):
    SUMMARIZE = "SUMMARIZE"
    EXTRACT_IDENTIFIERS = "EXTRACT_IDENTIFIERS"
    FILL_GAPS = "FILL_GAPS"
    COMPARE = "COMPARE"
