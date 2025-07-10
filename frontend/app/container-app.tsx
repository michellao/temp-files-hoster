'use client';

import ManageFiles from "@/components/manage-files";
import UploadFiles from "@/components/upload-files";
import { useUploadedData } from "@/lib/localstorage";

export default function ContainerApp() {
  const { counter, files, addFile, deleteFile } = useUploadedData();

  return (
    <>
      <UploadFiles addFile={addFile}/>
      <ManageFiles counter={counter} files={files} deleteFile={deleteFile}/>
    </>
  );
}
